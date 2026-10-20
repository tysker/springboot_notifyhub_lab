package io.github.tysker.notifyhub.search.service;

import io.github.tysker.notifyhub.search.config.OpenSearchProperties;
import io.github.tysker.notifyhub.search.document.NotificationDocument;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationIndexService {

    private final RestClient openSearchRestClient;
    private final ObjectMapper objectMapper;
    private final OpenSearchProperties openSearchProperties;

    public String index(NotificationDocument document) {
        if (document.getId() == null) {
            document.setId(UUID.randomUUID().toString());
        }

        String json = objectMapper.writeValueAsString(document);
        log.debug("Indexing: {}", json);

        String responseJson = openSearchRestClient
                .put()
                .uri("/{alias}/_doc/{id}", openSearchProperties.alias(), document.getId())
                .body(json)
                .retrieve()
                .body(String.class);

        JsonNode response = objectMapper.readTree(responseJson);
        log.info("Indexed {} into {} -> result={}, version={}",
                response.path("_id").asString(),
                response.path("_index").asString(),
                response.path("result").asString(),
                response.path("_version").asLong());

        return document.getId();
    }
}

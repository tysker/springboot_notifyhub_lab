package io.github.tysker.notifyhub.search.service;

import io.github.tysker.notifyhub.search.config.OpenSearchProperties;
import io.github.tysker.notifyhub.search.document.NotificationDocument;
import io.github.tysker.notifyhub.search.document.NotificationStatus;
import io.github.tysker.notifyhub.search.exception.OpenSearchException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.node.ObjectNode;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationIndexService {

    private final RestClient openSearchRestClient;
    private final ObjectMapper objectMapper;
    private final OpenSearchProperties openSearchProperties;

    // region INDEXING
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
    // endregion

    // region GET Queries
    public Optional<NotificationDocument> findById(String id) {
        try {
            String responseJson = openSearchRestClient
                    .get()
                    .uri("/{alias}/_doc/{id}", openSearchProperties.alias(), id)
                    .retrieve()
                    .body(String.class);

            JsonNode response = objectMapper.readTree(responseJson);
            JsonNode source = response.path("_source");
            return Optional.of(objectMapper.treeToValue(source, NotificationDocument.class));

        } catch (OpenSearchException e) {
            if (e.isNotFound()) {
                log.debug("Document {} not found", id);
                return Optional.empty();
            }
            throw e;
        }
    }
    // endregion

    // UPDATE Queries
    public String updateStatus(String id, NotificationStatus status, int retries) {
        ObjectNode body = objectMapper.createObjectNode();
        ObjectNode doc = body.putObject("doc");
        doc.put("status", status.name());
        doc.put("retries", retries);

        String json = objectMapper.writeValueAsString(body);
        log.debug("Updating {}: {}", id, json);

        String responseJson = openSearchRestClient.post()
                .uri("/{alias}/_update/{id}", openSearchProperties.alias(), id)
                .body(json)
                .retrieve()
                .body(String.class);

        JsonNode response = objectMapper.readTree(responseJson);
        String result = response.path("result").asString();
        log.info("Updated {} -> result={}, version={}", id, result, response.path("_version").asLong());
        return result;
    }

    // endregion

    // region DELETE Queries
    public boolean deleteById(String id) {
        try {
            String responseJson = openSearchRestClient
                    .delete()
                    .uri("/{alias}/_doc/{id}", openSearchProperties.alias(), id)
                    .retrieve()
                    .body(String.class);
            JsonNode response = objectMapper.readTree(responseJson);
            log.debug("Document {} deleted -> result={}, version={}",
                    id,
                    response.path("result").asString(),
                    response.path("_version").asLong());
            return true;
        } catch (OpenSearchException e) {
            if (e.isNotFound()) {
                log.debug("Document {} not found", id);
                return false;
            }
            throw e;
        }
    }
    // endregion
}

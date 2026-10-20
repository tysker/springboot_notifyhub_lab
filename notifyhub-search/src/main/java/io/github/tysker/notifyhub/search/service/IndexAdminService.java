package io.github.tysker.notifyhub.search.service;

import io.github.tysker.notifyhub.search.exception.OpenSearchException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.node.ObjectNode;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class IndexAdminService {

    private final RestClient openSearchRestClient;
    private final ObjectMapper objectMapper;

    public boolean indexExists(String index) {
        try {
            openSearchRestClient
                    .head()
                    .uri("/{index}", index)
                    .retrieve()
                    .toBodilessEntity();
            return true;
        } catch (OpenSearchException e) {
            if (e.isNotFound()) {
                return false;
            }
            throw e;
        }
    }

    public void createIndex(String index, ObjectNode body) {
        String json = objectMapper.writeValueAsString(body);
        log.debug("Creating index {}: {}", index, json);

        String responseJson = openSearchRestClient
                .put()
                .uri("/{index}", index)
                .body(json)
                .retrieve()
                .body(String.class);

        JsonNode response = objectMapper.readTree(responseJson);
        log.info("Created index {} -> acknowledged={}", index, response.path("acknowledged").asBoolean());
    }

    public Set<String> aliasTargets(String alias) {
        try {
            Map<String, Object> response = openSearchRestClient
                    .get()
                    .uri("/_alias/{alias}", alias)
                    .retrieve()
                    .body(new ParameterizedTypeReference<Map<String, Object>>() {
                    });

            return response == null ? Set.of() : new HashSet<>(response.keySet());
        } catch (OpenSearchException e) {
            if (e.isNotFound()) {
                log.debug("Alias '{}' does not exist", alias);
                return Set.of();
            }
            throw e;
        }
    }

    public void addAlias(String index, String alias) {
        ObjectNode body = objectMapper.createObjectNode();
        body.putArray("actions")
                .addObject().putObject("add")
                .put("index", index)
                .put("alias", alias);

        String responseJson = openSearchRestClient
                .post()
                .uri("/_aliases")
                .body(objectMapper.writeValueAsString(body))
                .retrieve()
                .body(String.class);

        JsonNode response = objectMapper.readTree(responseJson);
        log.info("Added alias '{}' to index '{}' -> acknowledged={}", alias, index, response.path("acknowledged").asBoolean());
    }


}
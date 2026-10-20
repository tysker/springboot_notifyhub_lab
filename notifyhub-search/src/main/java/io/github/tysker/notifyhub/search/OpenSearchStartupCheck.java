package io.github.tysker.notifyhub.search;

import io.github.tysker.notifyhub.search.config.OpenSearchProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClient;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

@Slf4j
@Component
@Order(1)
@RequiredArgsConstructor
public class OpenSearchStartupCheck implements ApplicationRunner {

    private final RestClient openSearchRestClient;
    private final ObjectMapper objectMapper;
    private final OpenSearchProperties openSearchProperties;

    @Override
    public void run(ApplicationArguments args) throws Exception {

        // === Check OpenSearch connection ===
        //JsonNode root = openSearchRestClient.get().uri("/").retrieve().body(JsonNode.class);
        String response;
        try {
            response = openSearchRestClient.get()
                    .uri("/")
                    .retrieve()
                    .body(String.class);
        } catch (ResourceAccessException e) {
            String errorMessage = "OpenSearch is not reachable at " + openSearchProperties.baseUrl() + " – is the container running? (docker compose ps)";
            if (openSearchProperties.failOnStartup()) {
                throw new IllegalStateException(errorMessage, e);
            }
            log.warn(errorMessage);
            return;
        }

        // === Check OpenSearch cluster health ===
        String healthJson = openSearchRestClient.get()
                .uri("/_cluster/health")
                .retrieve()
                .body(String.class);

        // === Parse and log the responses ===

        // Parse the root response to get cluster name and version
        JsonNode root = objectMapper.readTree(response);
        String clusterName = root.path("cluster_name").asString();
        String version = root.path("version").path("number").asString();
        log.info("Connected to OpenSearch cluster '{}', version {}", clusterName, version);

        // Parse the health response to get status and number of nodes
        JsonNode health = objectMapper.readTree(healthJson);
        String status = health.path("status").asString();
        int nodes = health.path("number_of_nodes").asInt();
        if ("green".equals(status)) {
            log.info("Cluster health: {} ({} node)", status, nodes);
        } else {
            log.warn("Cluster health is {} – check replicas/shards", status);
        }
    }
}


package io.github.tysker.notifyhub.search;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

@Slf4j
@Component
@RequiredArgsConstructor
public class OpenSearchStartupCheck implements ApplicationRunner {

    private final RestClient openSearchRestClient;
    private final ObjectMapper objectMapper;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        String response = openSearchRestClient.get()
                .uri("/")
                .retrieve()
                .body(String.class);

        JsonNode root = objectMapper.readTree(response);

        String clusterName = root.path("cluster_name").asString();
        String version = root.path("version").path("number").asString();

        log.info("Connected to OpenSearch cluster '{}', version {}", clusterName, version);
        log.info("OpenSearch response: {}", response);

    }
}


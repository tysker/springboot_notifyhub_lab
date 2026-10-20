package io.github.tysker.notifyhub.search.config;

import io.github.tysker.notifyhub.search.exception.OpenSearchException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestClient;
import tools.jackson.core.JacksonException;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

@Slf4j
@Configuration
@EnableConfigurationProperties(OpenSearchProperties.class)
public class OpenSearchConfig {

    @Bean
    RestClient openSearchRestClient(
            RestClient.Builder builder,
            OpenSearchProperties properties,
            ObjectMapper objectMapper) {
        log.info("OpenSearch base URL: {}", properties.baseUrl());
        return builder
                .baseUrl(properties.baseUrl())
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .defaultStatusHandler(HttpStatusCode::isError, (request, response) -> {
                    byte[] bytes = response.getBody().readAllBytes();
                    JsonNode body;
                    try {
                        body = bytes.length == 0 ? objectMapper.missingNode() : objectMapper.readTree(bytes);
                    } catch (JacksonException e) {
                        log.error("Error parsing OpenSearch response body", e);
                        body = objectMapper.missingNode();
                    }
                    int status = response.getStatusCode().value();
                    String type = body.path("error").path("type").asString();
                    String reason = body.path("error").path("reason").asString();
                    throw new OpenSearchException(status, type, reason);
                })
                .build();
    }
}

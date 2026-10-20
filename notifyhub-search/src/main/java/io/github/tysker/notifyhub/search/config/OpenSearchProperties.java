package io.github.tysker.notifyhub.search.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "notifyhub.opensearch")
public record OpenSearchProperties(String baseUrl, String alias, boolean failOnStartup) {
}

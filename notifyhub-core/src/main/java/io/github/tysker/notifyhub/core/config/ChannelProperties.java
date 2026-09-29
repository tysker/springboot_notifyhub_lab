package io.github.tysker.notifyhub.core.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Set;

@ConfigurationProperties(prefix = "notifyhub.channels")
public record ChannelProperties(String defaultChannel, Set<String> enabled) {
}

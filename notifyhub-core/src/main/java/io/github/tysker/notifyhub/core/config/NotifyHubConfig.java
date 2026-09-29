package io.github.tysker.notifyhub.core.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@EnableConfigurationProperties(ChannelProperties.class)
@PropertySource(value = "classpath:channels.properties")
@Configuration
public class NotifyHubConfig {
}

package io.github.tysker.notifyhub.core.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.time.Clock;

@EnableConfigurationProperties(ChannelProperties.class)
@PropertySource(value = "classpath:channels.properties")
@Configuration
public class NotifyHubConfig {

    @Bean
    public Clock clock() {
        return Clock.systemDefaultZone();
    }
}

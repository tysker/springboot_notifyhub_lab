package io.github.tysker.notifyhub.core.config;

import io.github.tysker.notifyhub.core.FallbackChannel;
import io.github.tysker.notifyhub.core.annotations.Channel;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.time.Clock;

@EnableScheduling
@EnableConfigurationProperties(ChannelProperties.class)
@PropertySource(value = "classpath:channels.properties")
@Configuration
public class NotifyHubConfig {

    @Bean
    public Clock clock() {
        return Clock.systemDefaultZone();
    }

    @Bean
    @Channel("fallback")
    public FallbackChannel fallbackChannel() {
        return new FallbackChannel();
    }

}

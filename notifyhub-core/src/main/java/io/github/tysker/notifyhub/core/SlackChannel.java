package io.github.tysker.notifyhub.core;

import io.github.tysker.notifyhub.api.Notification;
import io.github.tysker.notifyhub.api.NotificationChannel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Slf4j
@Profile("dev")
@Component
public class SlackChannel implements NotificationChannel {

    @Override
    public String name() {
        return "slack";
    }

    @Override
    public void send(Notification notification) {
        log.info("Sending Slack notification to {} with subject: {} and message: {}", notification.recipient(), notification.subject(), notification.message());
    }
}
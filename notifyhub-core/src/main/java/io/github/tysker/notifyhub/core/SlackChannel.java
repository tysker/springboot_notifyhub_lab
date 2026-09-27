package io.github.tysker.notifyhub.core;

import io.github.tysker.notifyhub.api.Notification;
import io.github.tysker.notifyhub.api.NotificationChannel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class SlackChannel implements NotificationChannel {

    @Override
    public String name() {
        return "Slack";
    }

    @Override
    public void send(Notification notification) {
        log.info("Sending Slack notification to {} with subject: {} and message: {}", notification.recipient(), notification.subject(), notification.message());
    }
}

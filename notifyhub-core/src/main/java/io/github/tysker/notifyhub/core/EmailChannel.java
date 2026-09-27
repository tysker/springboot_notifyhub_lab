package io.github.tysker.notifyhub.core;

import io.github.tysker.notifyhub.api.NotificationChannel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class EmailChannel implements NotificationChannel {

    @Override
    public String name() {
        return "Email";
    }

    @Override
    public void send(io.github.tysker.notifyhub.api.Notification notification) {
        log.info("Sending Email notification to {} with subject: {} and message: {}", notification.recipient(), notification.subject(), notification.message());
    }
}

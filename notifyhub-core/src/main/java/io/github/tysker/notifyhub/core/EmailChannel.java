package io.github.tysker.notifyhub.core;

import io.github.tysker.notifyhub.api.Notification;
import io.github.tysker.notifyhub.api.NotificationChannel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Slf4j
@Primary
@Component
public class EmailChannel implements NotificationChannel {

    private final String sender;

    @Override
    public String name() {
        return "email";
    }

    public EmailChannel(@Value("${notifyhub.email.sender:default@example.com}") String sender) {
        this.sender = sender;
    }

    @Override
    public void send(Notification notification) {
        log.info("Sending Email notification from sender {} to {} with subject: {}. Message: {}", sender, notification.recipient(), notification.subject(), notification.message());
    }
}

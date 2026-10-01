package io.github.tysker.notifyhub.core;

import io.github.tysker.notifyhub.api.Notification;
import io.github.tysker.notifyhub.api.NotificationChannel;
import io.github.tysker.notifyhub.core.annotations.Channel;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Slf4j
@Primary
@Component
@Channel("email")
public class EmailChannel implements NotificationChannel {

    private final String sender;

    @Override
    public String name() {
        return "email";
    }

    public EmailChannel(@Value("${notifyhub.email.sender:default@example.com}") String sender) {
        this.sender = sender;
    }

    @PostConstruct
    public void connect() {
        log.info("[EMAIL] Connecting to SMTP server as {}", sender);
    }

    @PreDestroy
    public void disconnect() {
        log.info("[EMAIL] Disconnecting from SMTP server");
    }

    @Override
    public void send(Notification notification) {
        log.info("Sending Email notification from sender {} to {} with subject: {}. Message: {}", sender, notification.recipient(), notification.subject(), notification.message());
    }
}

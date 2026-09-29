package io.github.tysker.notifyhub.core;

import io.github.tysker.notifyhub.api.Notification;
import io.github.tysker.notifyhub.api.NotificationChannel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class SmsChannel implements NotificationChannel {

    private final int maxLength;

    public SmsChannel(@Value("${notifyhub.sms.max-length}") int maxLength) {
        this.maxLength = maxLength;
    }

    @Override
    public String name() {
        return "sms";
    }

    @Override
    public void send(Notification notification) {
        String message;
        if (notification.message().length() > maxLength) {
            message = notification.message().substring(0, maxLength);
        } else {
            message = notification.message();
        }
        log.info("Sending SMS notification to {} with subject: {}. Message: {}", notification.recipient(), notification.subject(), message);
    }
}

package io.github.tysker.notifyhub.core;

import io.github.tysker.notifyhub.api.Notification;
import io.github.tysker.notifyhub.api.NotificationChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FallbackChannel implements NotificationChannel {

    private static final Logger log = LoggerFactory.getLogger(FallbackChannel.class);

    @Override
    public String name() {
        return "fallback";
    }

    @Override
    public void send(Notification notification) {
        log.info("[FALLBACK] to {}: {}", notification.recipient(), notification.message());
    }
}

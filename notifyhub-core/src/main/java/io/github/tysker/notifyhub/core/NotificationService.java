package io.github.tysker.notifyhub.core;

import io.github.tysker.notifyhub.api.Notification;
import io.github.tysker.notifyhub.api.NotificationChannel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Service
public class NotificationService {

    private final List<NotificationChannel> channels;

    public void send(Notification notification) {
        for (NotificationChannel channel : channels) {
            log.info("Sending notification via {} channel", channel.name());
            channel.send(notification);
        }
    }
}

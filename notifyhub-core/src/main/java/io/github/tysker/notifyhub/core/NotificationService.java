package io.github.tysker.notifyhub.core;

import io.github.tysker.notifyhub.api.Notification;
import io.github.tysker.notifyhub.api.NotificationChannel;
import io.github.tysker.notifyhub.core.exceptions.ChannelException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
public class NotificationService {

    private final Map<String, NotificationChannel> channelMap;

    public NotificationService(List<NotificationChannel> channelList) {
        this.channelMap = channelList
                .stream()
                .collect(Collectors.toMap(
                        NotificationChannel::name,
                        channel -> channel));
    }

    public void send(Notification notification) {
        NotificationChannel channel = channelMap.get(notification.channel().toLowerCase());

        if (channel != null) {
            log.info("Sending notification via {} channel", channel.name());
            channel.send(notification);
        } else {
            throw new ChannelException("Notification channel not found: " + notification.channel());
        }
    }
}

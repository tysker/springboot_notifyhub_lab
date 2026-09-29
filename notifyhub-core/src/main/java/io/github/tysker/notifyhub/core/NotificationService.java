package io.github.tysker.notifyhub.core;

import io.github.tysker.notifyhub.api.Notification;
import io.github.tysker.notifyhub.api.NotificationChannel;
import io.github.tysker.notifyhub.core.config.ChannelProperties;
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
    private final ChannelProperties channelProperties;

    public NotificationService(List<NotificationChannel> channelList, ChannelProperties channelProperties) {
        this.channelMap = channelList
                .stream()
                .collect(Collectors.toMap(
                        NotificationChannel::name,
                        channel -> channel));

        this.channelProperties = channelProperties;
    }

    public void send(Notification notification) {
        String channelName = notification.channel();

        channelName = channelName == null || channelName.trim().isBlank() ? channelProperties.defaultChannel() : channelName.toLowerCase();

        if (!channelProperties.enabled().contains(channelName))
            throw new ChannelException("Notification channel not found: " + channelName);

        NotificationChannel channel = channelMap.get(channelName);

        channel.send(notification);
    }
}

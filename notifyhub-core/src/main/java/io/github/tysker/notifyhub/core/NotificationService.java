package io.github.tysker.notifyhub.core;

import io.github.tysker.notifyhub.api.Notification;
import io.github.tysker.notifyhub.api.NotificationChannel;
import io.github.tysker.notifyhub.api.NotificationSentEvent;
import io.github.tysker.notifyhub.core.config.ChannelProperties;
import io.github.tysker.notifyhub.core.exceptions.ChannelException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
public class NotificationService {

    private final Map<String, NotificationChannel> channelMap;
    private final ChannelProperties channelProperties;
    private final Clock clock;
    private final ApplicationEventPublisher publisher;

    public NotificationService(
            List<NotificationChannel> channelList,
            ChannelProperties channelProperties,
            Clock clock,
            ApplicationEventPublisher publisher) {

        this.channelMap = channelList
                .stream()
                .collect(Collectors.toMap(
                        NotificationChannel::name,
                        channel -> channel));

        this.channelProperties = channelProperties;
        this.clock = clock;
        this.publisher = publisher;

        log.info("Registered channels: {}", channelMap.keySet());
    }

    public void send(Notification notification) {
        String channelName = notification.channel();
        channelName = channelName == null || channelName.isBlank() ? channelProperties.defaultChannel() : channelName.toLowerCase();

        if (!channelProperties.enabled().contains(channelName))
            throw new ChannelException("Channel is disabled: " + channelName);

        NotificationChannel channel = channelMap.get(channelName);
        if (channel == null) {
            throw new ChannelException("Channel not found: " + channelName);
        }

        Instant now = Instant.now(clock);
        log.info("Sending via {} at {}", channelName, now);
        channel.send(notification);
        publisher.publishEvent(new NotificationSentEvent(notification, channelName, now));
    }
}

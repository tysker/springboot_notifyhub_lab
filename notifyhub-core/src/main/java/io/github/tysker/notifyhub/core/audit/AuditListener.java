package io.github.tysker.notifyhub.core.audit;

import io.github.tysker.notifyhub.api.NotificationSentEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Slf4j
@Component
public class AuditListener {

    private final Map<String, AtomicLong> sentPerChannel = new ConcurrentHashMap<>();

    @EventListener
    public void onNotificationSent(NotificationSentEvent event) {
        sentPerChannel.computeIfAbsent(event.channel(), k -> new AtomicLong()).incrementAndGet();
        log.info("[AUDIT] {} sent via {} at {}", event.notification().recipient(), event.channel(), event.sentAt());
    }

    public Map<String, Long> snapshot() {
        return sentPerChannel.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue().get()));
    }
}

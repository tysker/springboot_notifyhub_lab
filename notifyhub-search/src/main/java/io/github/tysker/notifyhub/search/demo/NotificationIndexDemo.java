package io.github.tysker.notifyhub.search.demo;

import io.github.tysker.notifyhub.search.document.NotificationChannel;
import io.github.tysker.notifyhub.search.document.NotificationDocument;
import io.github.tysker.notifyhub.search.document.NotificationStatus;
import io.github.tysker.notifyhub.search.service.NotificationIndexService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
@Order(2)
@RequiredArgsConstructor
public class NotificationIndexDemo implements ApplicationRunner {

    private final NotificationIndexService indexService;

    @Override
    public void run(ApplicationArguments args) {
        NotificationDocument doc = NotificationDocument.builder()
                .channel(NotificationChannel.EMAIL)
                .recipient("java@example.com")
                .subject("Sent from Java")
                .message("This notification was indexed by notifyhub.")
                .status(NotificationStatus.SENT)
                .retries(0)
                .durationMs(42)
                .createdAt(Instant.now())
                .build();

        indexService.index(doc);
    }
}

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
import java.util.Optional;

@Component
@Order(2)
@RequiredArgsConstructor
public class NotificationIndexDemo implements ApplicationRunner {

    private final NotificationIndexService indexService;

    @Override
    public void run(ApplicationArguments args) {
//        String id = index();
//        String result = update(id, NotificationStatus.FAILED, 2);
//        boolean ok = delete(id);
        NotificationDocument notificationDocument = findByID("a1b2c3d4-0000-4000-8000-000000000007");
        System.out.println(notificationDocument);
    }


    public String index() {
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

        return indexService.index(doc);
    }

    public NotificationDocument findByID(String id) {
        Optional<NotificationDocument> notificationDocument = indexService.findById(id);
        return notificationDocument.orElse(null);
    }

    public String update(String id, NotificationStatus status, int retries) {
        return indexService.updateStatus(id, status, retries);
    }

    public boolean delete(String id) {
        return indexService.deleteById(id);
    }

}

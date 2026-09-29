package io.github.tysker.notifyhub;

import io.github.tysker.notifyhub.api.Notification;
import io.github.tysker.notifyhub.core.NotificationService;
import io.github.tysker.notifyhub.core.exceptions.ChannelException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@Slf4j
@SpringBootApplication
public class NotifyhubAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(NotifyhubAppApplication.class, args);
    }

    private void trySend(NotificationService service, Notification notification) {
        try {
            service.send(notification);
        } catch (ChannelException e) {
            log.warn("Send failed: {}", e.getMessage());
        }
    }

    @Bean
    public CommandLineRunner sendTestNotifications(NotificationService notificationService) {
        return args -> {
            trySend(notificationService, Notification.builder()
                    .recipient("test@example.com")
                    .subject("Test subject email")
                    .message("This is a test email.")
                    .channel("email")
                    .build());
            trySend(notificationService, Notification.builder()
                    .recipient("+1234567890")
                    .subject("Test subject sms")
                    .message("This is a test sms notification.")
                    .channel("sms")
                    .build());
            trySend(notificationService, Notification.builder()
                    .recipient("test@example.com")
                    .subject("Test subject email")
                    .message("This is a test email without channel name.")
                    .build());
            trySend(notificationService, Notification.builder()
                    .recipient("slack-channel")
                    .subject("Test subject slack")
                    .message("This is a test slack notification.")
                    .channel("slack")
                    .build());
        };

    }

}



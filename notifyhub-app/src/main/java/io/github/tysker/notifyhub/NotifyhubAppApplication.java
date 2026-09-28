package io.github.tysker.notifyhub;

import io.github.tysker.notifyhub.api.Notification;
import io.github.tysker.notifyhub.api.NotificationChannel;
import io.github.tysker.notifyhub.core.NotificationService;
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

    @Bean
    public CommandLineRunner sendTestNotifications(NotificationService notificationService, NotificationChannel channel) {
        return args -> {
            log.info("Sending test notification: {}", channel.name());
            notificationService.send(Notification.builder()
                    .recipient("test@example.com")
                    .subject("Test Notification")
                    .message("This is a test notification.")
                    .channel("email")
                    .build());
        };

    }

}

package io.github.tysker.notifyhub.api;

public interface NotificationChannel {
    String name();
    void send(Notification notification);
}

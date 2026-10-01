package io.github.tysker.notifyhub.core;

import io.github.tysker.notifyhub.api.Notification;
import io.github.tysker.notifyhub.api.NotificationChannel;
import io.github.tysker.notifyhub.core.annotations.Channel;
import org.springframework.stereotype.Service;

@Service
public class AdminAlertService {

    private final NotificationChannel channel;

    public AdminAlertService(@Channel("sms") NotificationChannel channel) {
        this.channel = channel;
    }

    public void alert(String message) {
        channel.send(new Notification("+450000000", "ALERT", message, "sms"));
    }

}

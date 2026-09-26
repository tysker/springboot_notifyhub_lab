package io.github.tysker.notifyhubcore;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    @Autowired
    public NotificationService() {
        System.out.println("NotificationController initialized");
    }
}

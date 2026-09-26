package io.github.tysker.notifyhubcore;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j(topic = "notifyhub")
@Service
public class NotificationService {

    @Autowired
    public NotificationService() {
        log.info("init() --> NotificationService initialized");
    }
}

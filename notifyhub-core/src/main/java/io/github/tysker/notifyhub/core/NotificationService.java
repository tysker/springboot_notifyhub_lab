package io.github.tysker.notifyhub.core;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class NotificationService {

    public NotificationService() {
        log.info("init() --> NotificationService initialized");
    }
}

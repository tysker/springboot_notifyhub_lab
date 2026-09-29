package io.github.tysker.notifyhub.core.audit;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class StatsReporter {

    private final AuditListener auditListener;

    @Scheduled(cron = "${notifyhub.stats.cron}")
    public void report() {
        log.info("[STATS] {}", auditListener.snapshot());
    }
}

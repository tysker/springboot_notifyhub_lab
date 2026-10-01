package io.github.tysker.notifyhub.core.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class LoggingAspect {

    @Around("@annotation(io.github.tysker.notifyhub.core.annotations.LogExecutionTime)")
    public Object logTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.nanoTime();
        try {
            return joinPoint.proceed();
        } finally {
            long ms = (System.nanoTime() - start) / 1_000_000;
            log.info("[TIMING] {} took {} ms", joinPoint.getSignature().toShortString(), ms);
        }
    }
}
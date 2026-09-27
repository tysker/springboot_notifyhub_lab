package io.github.tysker.notifyhub.api;

import lombok.Builder;

@Builder
public record Notification
        (
                String recipient,
                String subject,
                String message,
                String channel
        ) {
}

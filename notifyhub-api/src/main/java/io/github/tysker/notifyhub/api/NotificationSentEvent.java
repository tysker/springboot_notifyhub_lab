package io.github.tysker.notifyhub.api;

import java.time.Instant;

public record NotificationSentEvent(Notification notification, String channel, Instant sentAt) {
}

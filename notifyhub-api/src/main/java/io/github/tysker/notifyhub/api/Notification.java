package io.github.tysker.notifyhub.api;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record Notification
        (

                @NotBlank(message = "Recipient cannot be blank")
                String recipient,
                String subject,
                String message,
                String channel
        ) {
}

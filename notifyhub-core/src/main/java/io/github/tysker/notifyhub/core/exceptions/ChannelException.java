package io.github.tysker.notifyhub.core.exceptions;

import lombok.Getter;

@Getter
public class ChannelException extends RuntimeException {

    public ChannelException(String message) {
        super(message);
    }
}

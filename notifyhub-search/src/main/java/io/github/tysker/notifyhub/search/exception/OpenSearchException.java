package io.github.tysker.notifyhub.search.exception;

import lombok.Getter;

@Getter
public class OpenSearchException extends RuntimeException {

    private final int status;
    private final String type;
    private final String reason;

    public OpenSearchException(int status, String type, String reason) {
        super("OpenSearch error " + status + " [" + type + "]: " + reason);
        this.status = status;
        this.type = type;
        this.reason = reason;
    }

    public boolean isNotFound() {
        return status == 404;
    }
}

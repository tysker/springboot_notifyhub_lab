package io.github.tysker.notifyhub.search.exception;

import lombok.Getter;

@Getter
public class OpenSearchException extends RuntimeException {

    public int status;
    public String type;
    public String reason;

    public OpenSearchException(int status, String type, String reason) {
        super();
        this.status = status;
        this.type = type;
        this.reason = reason;
    }

    public boolean isNotFound() {
        return status == 404;
    }
}

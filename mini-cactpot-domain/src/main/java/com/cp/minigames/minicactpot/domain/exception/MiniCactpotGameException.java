package com.cp.minigames.minicactpot.domain.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

public class MiniCactpotGameException extends RuntimeException {
    @Getter
    private final HttpStatus status;

    public MiniCactpotGameException() {
        super();
        this.status = HttpStatus.INTERNAL_SERVER_ERROR;
    }

    public MiniCactpotGameException(HttpStatus status) {
        super();
        this.status = status;
    }

    public MiniCactpotGameException(String message) {
        super(message);
        this.status = HttpStatus.INTERNAL_SERVER_ERROR;
    }

    public MiniCactpotGameException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

    public MiniCactpotGameException(String message, Throwable cause) {
        super(message, cause);
        this.status = HttpStatus.INTERNAL_SERVER_ERROR;
    }

    public MiniCactpotGameException(String message, Throwable cause, HttpStatus status) {
        super(message, cause);
        this.status = status;
    }

    public MiniCactpotGameException(Throwable cause) {
        super(cause);
        this.status = HttpStatus.INTERNAL_SERVER_ERROR;
    }

    public MiniCactpotGameException(Throwable cause, HttpStatus status) {
        super(cause);
        this.status = status;
    }
}

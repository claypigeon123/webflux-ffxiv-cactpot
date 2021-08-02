package com.cp.minigames.minicactpotservice.exception;

public class MiniCactpotGameException extends RuntimeException {
    public MiniCactpotGameException() {
        super("An unknown error has occurred while processing your request");
    }

    public MiniCactpotGameException(String message) {
        super(message);
    }
}

package com.cp.minigames.minicactpotservice.exception;

import com.cp.minigames.minicactpotservice.exception.base.MiniCactpotGameException;

public class TicketNotFoundException extends MiniCactpotGameException {
    public TicketNotFoundException(String id) {
        super(String.format("Mini cactpot ticket with ID %s not found", id));
    }
}

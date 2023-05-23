package com.cp.minigames.minicactpot.domain.exception.impl;

import com.cp.minigames.minicactpot.domain.exception.MiniCactpotGameException;
import org.springframework.http.HttpStatus;

public class TicketNotFoundException extends MiniCactpotGameException {
    public TicketNotFoundException(String id) {
        super(String.format("Mini cactpot ticket with ID %s not found", id), HttpStatus.NOT_FOUND);
    }
}

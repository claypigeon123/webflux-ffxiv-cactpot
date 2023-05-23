package com.cp.minigames.minicactpot.domain.exception.impl;

import com.cp.minigames.minicactpot.domain.exception.MiniCactpotGameException;
import org.springframework.http.HttpStatus;

public class TicketAlreadyCompleteException extends MiniCactpotGameException {
    public TicketAlreadyCompleteException() {
        super("The given mini cactpot ticket has already been completed", HttpStatus.CONFLICT);
    }
}

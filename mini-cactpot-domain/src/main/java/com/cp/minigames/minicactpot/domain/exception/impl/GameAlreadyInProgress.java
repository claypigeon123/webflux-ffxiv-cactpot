package com.cp.minigames.minicactpot.domain.exception.impl;

import com.cp.minigames.minicactpot.domain.exception.MiniCactpotGameException;
import org.springframework.http.HttpStatus;

public class GameAlreadyInProgress extends MiniCactpotGameException {
    public GameAlreadyInProgress() {
        super("Cannot start a new game until existing ones belonging to this IP are finished", HttpStatus.TOO_MANY_REQUESTS);
    }
}

package com.cp.minigames.minicactpot.domain.exception.impl;

import com.cp.minigames.minicactpot.domain.exception.MiniCactpotGameException;
import org.springframework.http.HttpStatus;

public class IpCannotBeDetermined extends MiniCactpotGameException {
    public IpCannotBeDetermined() {
        super("Remote address could not be determined", HttpStatus.FORBIDDEN);
    }
}

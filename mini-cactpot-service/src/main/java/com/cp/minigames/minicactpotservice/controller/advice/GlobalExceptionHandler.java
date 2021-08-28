package com.cp.minigames.minicactpotservice.controller.advice;

import com.cp.minigames.minicactpotservice.exception.base.MiniCactpotGameException;
import com.cp.minigames.minicactpotservice.model.error.MiniCactpotError;
import com.cp.minigames.minicactpotservice.model.error.MiniCactpotFieldError;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.support.WebExchangeBindException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MiniCactpotGameException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Mono<MiniCactpotError> genericError(MiniCactpotGameException e) {
        return Mono.just(MiniCactpotError.builder()
            .error(e.getMessage())
            .build()
        );
    }

    @ExceptionHandler(WebExchangeBindException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Flux<MiniCactpotFieldError> invalidBody(WebExchangeBindException e) {
        return Flux.fromIterable(e.getBindingResult().getFieldErrors())
            .map(error -> MiniCactpotFieldError.builder()
                .field(error.getField())
                .error(error.getDefaultMessage())
                .build()
            );
    }
}

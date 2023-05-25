package com.cp.minigames.minicactpotservice.controller.advice;

import com.cp.minigames.minicactpot.domain.exception.MiniCactpotGameException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class GlobalExceptionHandler {

    @ExceptionHandler(ConstraintViolationException.class)
    public Mono<String> constraintViolation(ConstraintViolationException e) {
        return Flux.fromIterable(e.getConstraintViolations())
            .map(ConstraintViolation::getMessage)
            .filter(violation -> !violation.isBlank())
            .collectList()
            .flatMap(violations -> Mono.error(new ResponseStatusException(HttpStatus.BAD_REQUEST, String.join(", ", violations))));
    }

    @ExceptionHandler(WebExchangeBindException.class)
    public Mono<String> invalidBody(WebExchangeBindException e) {
        return Flux.fromIterable(e.getBindingResult().getFieldErrors())
            .mapNotNull(FieldError::getDefaultMessage)
            .collectList()
            .flatMap(violations -> Mono.error(new ResponseStatusException(HttpStatus.BAD_REQUEST, String.join(", ", violations))));

    }

    @ExceptionHandler(MiniCactpotGameException.class)
    public Mono<String> domainException(MiniCactpotGameException e) {
        return Mono.error(new ResponseStatusException(e.getStatus(), e.getMessage()));
    }
}

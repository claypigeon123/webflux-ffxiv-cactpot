package com.cp.minigames.minicactpotservice.validation.annotation;

import com.cp.minigames.minicactpotservice.validation.WinningsMapValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = WinningsMapValidator.class)
@Target( { ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidWinningsMap {
    String message() default "winnings map must contain mappings for keys 6 through 24";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

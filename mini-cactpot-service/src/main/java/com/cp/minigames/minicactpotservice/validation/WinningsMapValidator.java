package com.cp.minigames.minicactpotservice.validation;

import com.cp.minigames.minicactpotservice.validation.annotation.ValidWinningsMap;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.NonNull;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class WinningsMapValidator implements ConstraintValidator<ValidWinningsMap, Map<Integer, Integer>> {

    private static final Set<Integer> MUST_INCLUDE = IntStream.range(6, 25).boxed().collect(Collectors.toSet());

    @Override
    public boolean isValid(@NonNull Map<Integer, Integer> map, ConstraintValidatorContext context) {
        return map.keySet().size() == MUST_INCLUDE.size() && map.keySet().containsAll(MUST_INCLUDE);
    }

}

package com.cp.minigames.minicactpot.domain.model.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.NONE)
public class AggregateConstants {
    // generic
    public static final String ID = "id";

    // mini cactpot aggregates
    public static final String CREATED_DATE = "createdDate";
    public static final String UPDATED_DATE = "updatedDate";

    public static final String CREATED_DATE_FROM = CREATED_DATE + ".from";
    public static final String CREATED_DATE_TO = CREATED_DATE + ".to";
    public static final String UPDATED_DATE_FROM = UPDATED_DATE + ".from";
    public static final String UPDATED_DATE_TO = UPDATED_DATE + ".to";

    public static final String STAGE = "stage";
    public static final String STAGE_NOT = STAGE + ".not";

    public static final String WINNINGS = "winnings";
    public static final String WINNINGS_FROM = WINNINGS + ".from";
    public static final String WINNINGS_TO = WINNINGS + ".to";
}

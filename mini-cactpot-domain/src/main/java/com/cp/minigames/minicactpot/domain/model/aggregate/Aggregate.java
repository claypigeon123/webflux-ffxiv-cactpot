package com.cp.minigames.minicactpot.domain.model.aggregate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.OffsetDateTime;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class Aggregate {
    protected String id;
    protected OffsetDateTime createdDate;
    protected OffsetDateTime updatedDate;
}

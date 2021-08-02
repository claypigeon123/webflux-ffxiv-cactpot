package com.cp.minigames.minicactpotservice.model.aggregate;

import com.cp.minigames.minicactpotservice.model.attributes.MiniCactpotBoard;
import com.cp.minigames.minicactpotservice.model.attributes.MiniCactpotPublicBoard;
import com.cp.minigames.minicactpotservice.model.attributes.MiniCactpotSelection;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.couchbase.core.mapping.Document;
import org.springframework.data.couchbase.core.mapping.id.GeneratedValue;
import org.springframework.data.couchbase.core.mapping.id.GenerationStrategy;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document
public class MiniCactpotAggregate {
    @Id
    @GeneratedValue(strategy = GenerationStrategy.UNIQUE)
    private UUID id;

    private Integer nScratched;

    private Boolean canSelect;

    private Boolean isDone;

    private Integer winnings;

    private MiniCactpotSelection selection;

    private MiniCactpotBoard board;

    private MiniCactpotPublicBoard publicBoard;

    private String createdDate;
}

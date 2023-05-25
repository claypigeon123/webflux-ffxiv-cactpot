package com.cp.minigames.minicactpotservice.mapper

import com.cp.minigames.minicactpot.domain.model.attributes.MiniCactpotGameStage
import com.cp.minigames.minicactpot.domain.model.attributes.MiniCactpotNode
import com.cp.minigames.minicactpot.domain.model.attributes.MiniCactpotPublicNode
import com.cp.minigames.minicactpot.domain.model.attributes.MiniCactpotSelection
import com.cp.minigames.minicactpotservice.config.props.MiniCactpotProperties
import com.cp.minigames.minicactpotservice.util.RandomNumberGenerator
import spock.lang.Shared
import spock.lang.Specification

import java.time.*

class MiniCactpotMapperTest extends Specification {

    @Shared
    Clock clock = Clock.fixed(Instant.now(), ZoneId.of(ZoneOffset.UTC.getId()))

    MiniCactpotProperties properties
    RandomNumberGenerator rng

    MiniCactpotMapper miniCactpotMapper

    void setup() {
        Map<Integer, Integer> fedMap = new HashMap<>()
        fedMap.put(6, 10000)
        fedMap.put(7, 36)
        fedMap.put(8, 720)
        fedMap.put(9, 360)
        fedMap.put(10, 80)
        fedMap.put(11, 252)
        fedMap.put(12, 108)
        fedMap.put(13, 72)
        fedMap.put(14, 54)
        fedMap.put(15, 180)
        fedMap.put(16, 72)
        fedMap.put(17, 180)
        fedMap.put(18, 119)
        fedMap.put(19, 36)
        fedMap.put(20, 306)
        fedMap.put(21, 1080)
        fedMap.put(22, 144)
        fedMap.put(23, 1800)
        fedMap.put(24, 3600)
        properties = new MiniCactpotProperties(fedMap)
        rng = Mock()

        miniCactpotMapper = new MiniCactpotMapper(properties, rng, clock)
    }

    def "mapping private board to public"() {
        given:
        List<MiniCactpotNode> input = List.of(
            new MiniCactpotNode(a, aR),
            new MiniCactpotNode(b, bR),
            new MiniCactpotNode(c, cR),
            new MiniCactpotNode(d, dR),
            new MiniCactpotNode(e, eR),
            new MiniCactpotNode(f, fR),
            new MiniCactpotNode(g, gR),
            new MiniCactpotNode(h, hR),
            new MiniCactpotNode(i, iR),
        )

        List<MiniCactpotPublicNode> expected = List.of(
            new MiniCactpotPublicNode(aR ? a : -1),
            new MiniCactpotPublicNode(bR ? b : -1),
            new MiniCactpotPublicNode(cR ? c : -1),
            new MiniCactpotPublicNode(dR ? d : -1),
            new MiniCactpotPublicNode(eR ? e : -1),
            new MiniCactpotPublicNode(fR ? f : -1),
            new MiniCactpotPublicNode(gR ? g : -1),
            new MiniCactpotPublicNode(hR ? h : -1),
            new MiniCactpotPublicNode(iR ? i : -1),
        )

        when:
        def result = miniCactpotMapper.mapPrivateBoardToPublic(input)

        then:
        result == expected

        where:
        a | aR    | b | bR    | c | cR    | d | dR    | e | eR    | f | fR    | g | gR    | h | hR    | i | iR
        1 | false | 2 | false | 3 | false | 4 | false | 5 | false | 6 | false | 7 | false | 8 | false | 9 | false
        4 | true  | 3 | true  | 2 | true  | 1 | true  | 5 | true  | 6 | true  | 9 | true  | 7 | true  | 8 | true
        1 | true  | 2 | false | 3 | false | 4 | false | 5 | false | 6 | true  | 7 | false | 8 | false | 9 | false
        9 | true  | 8 | true  | 7 | true  | 6 | true  | 5 | true  | 4 | false | 3 | false | 2 | true  | 1 | false
    }

    def "initializing a new game"() {
        when:
        def aggregate = miniCactpotMapper.initializeNewGame().block()

        then:
        rng.generate(1, 9) >>> [1, 2, 3, 4, 5, 6, 7, 8, 9]

        aggregate.id != null
        aggregate.createdDate == OffsetDateTime.now(clock)
        aggregate.updatedDate == OffsetDateTime.now(clock)
        aggregate.stage == MiniCactpotGameStage.SCRATCHING_FIRST
        aggregate.selection == MiniCactpotSelection.NONE
        aggregate.winnings == null

        aggregate.board.stream()
            .map(node -> node.getNumber())
            .reduce(0, (subtotal, number) -> subtotal + number)
            == (1 + 2 + 3 + 4 + 5 + 6 + 7 + 8 + 9)

        aggregate.board.stream()
            .filter(node -> node.getIsRevealed())
            .count()
            == 1
    }
}

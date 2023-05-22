package com.cp.minigames.minicactpotservice.util

import com.cp.minigames.minicactpot.domain.model.attributes.MiniCactpotNode
import com.cp.minigames.minicactpot.domain.model.attributes.MiniCactpotPublicNode
import spock.lang.*

class MiniCactpotBoardUtilsTest extends Specification {

    MiniCactpotBoardUtils utils

    void setup() {
        utils = new MiniCactpotBoardUtils(
            new RandomNumberGenerator()
        )
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
        def result = utils.mapPrivateBoardToPublic(input)

        then:
        result == expected

        where:
        a | aR    | b | bR    | c | cR    | d | dR    | e | eR    | f | fR    | g | gR    | h | hR    | i | iR
        1 | false | 2 | false | 3 | false | 4 | false | 5 | false | 6 | false | 7 | false | 8 | false | 9 | false
        4 | true  | 3 | true  | 2 | true  | 1 | true  | 5 | true  | 6 | true  | 9 | true  | 7 | true  | 8 | true
        1 | true  | 2 | false | 3 | false | 4 | false | 5 | false | 6 | true  | 7 | false | 8 | false | 9 | false
        9 | true  | 8 | true  | 7 | true  | 6 | true  | 5 | true  | 4 | false | 3 | false | 2 | true  | 1 | false
    }

    def "initializing a new board"() {
        when:
        def result = utils.initializeNodes()

        then:
        result.size() == 9

        result.stream()
            .map(node -> node.getNumber())
            .reduce(0, (subtotal, number) -> subtotal + number)
        == (1 + 2 + 3 + 4 + 5 + 6 + 7 + 8 + 9)

        result.stream()
            .filter(node -> node.getIsRevealed())
            .count()
        == 1
    }
}

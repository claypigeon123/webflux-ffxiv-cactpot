package com.cp.minigames.minicactpotservice.service

import com.cp.minigames.minicactpot.domain.model.aggregate.MiniCactpotAggregate
import com.cp.minigames.minicactpot.domain.model.attributes.MiniCactpotPublicNode
import com.cp.minigames.minicactpot.domain.model.response.StartMiniCactpotGameResponse
import com.cp.minigames.minicactpotservice.config.props.MiniCactpotProperties
import com.cp.minigames.minicactpotservice.repository.MiniCactpotAggregateRepository
import com.cp.minigames.minicactpotservice.repository.base.ReactiveRepository
import com.cp.minigames.minicactpotservice.util.MiniCactpotBoardUtils
import reactor.core.publisher.Mono
import spock.lang.Specification

import java.time.Clock
import java.time.Instant
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

import static com.cp.minigames.minicactpot.domain.model.attributes.MiniCactpotGameStage.SCRATCHING_FIRST

class MiniCactpotServiceTest extends Specification {

    // Mocks
    ReactiveRepository<MiniCactpotAggregate, String> repository
    MiniCactpotProperties properties

    // Target class
    MiniCactpotService service

    // misc
    Map<Integer, Integer> fedMap

    void setup() {
        repository = Mock(MiniCactpotAggregateRepository.class)
        fedMap = new HashMap<>()
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

        service = new MiniCactpotService(
            repository,
            properties,
            new MiniCactpotBoardUtils(),
            DateTimeFormatter.ISO_OFFSET_DATE_TIME.withZone(ZoneId.of(ZoneOffset.UTC.getId())),
            Clock.fixed(Instant.now(), ZoneId.of(ZoneOffset.UTC.getId()))
        )
    }

    def "get winnings map"() {
        when:
        def result = service.getWinningsMap().block()

        then:
        result == fedMap
    }

    def "start a new game"() {
        given:
        def id = UUID.randomUUID().toString()
        def expected = StartMiniCactpotGameResponse.builder()
            .id(id)
            .stage(SCRATCHING_FIRST)
            .board(List.of(
                new MiniCactpotPublicNode(-1),
                new MiniCactpotPublicNode(4),
                new MiniCactpotPublicNode(-1),
                new MiniCactpotPublicNode(-1),
                new MiniCactpotPublicNode(-1),
                new MiniCactpotPublicNode(-1),
                new MiniCactpotPublicNode(-1),
                new MiniCactpotPublicNode(-1),
                new MiniCactpotPublicNode(-1),
            ))

        when:
        def result = service.startGame().block()

        then:
        1 * repository.upsert(_) >> { input, sg ->
            return Mono.just(input)
        }

        result.getId() != null
        result.getBoard().size() == 9
        result.getStage() == SCRATCHING_FIRST
    }
}

package com.cp.minigames.minicactpotservice.service

import com.cp.minigames.minicactpot.domain.model.aggregate.MiniCactpotAggregate
import com.cp.minigames.minicactpot.domain.model.attributes.MiniCactpotPublicNode
import com.cp.minigames.minicactpot.domain.model.response.StartMiniCactpotGameResponse
import com.cp.minigames.minicactpotservice.config.props.MiniCactpotProperties
import com.cp.minigames.minicactpotservice.mapper.MiniCactpotMapper
import com.cp.minigames.minicactpotservice.repository.AbstractReactiveMongoRepository
import com.cp.minigames.minicactpotservice.util.RandomNumberGenerator
import reactor.core.publisher.Mono
import spock.lang.Shared
import spock.lang.Specification

import java.time.Clock
import java.time.Instant
import java.time.ZoneId
import java.time.ZoneOffset

import static com.cp.minigames.minicactpot.domain.model.attributes.MiniCactpotGameStage.SCRATCHING_FIRST

class MiniCactpotServiceTest extends Specification {

    @Shared
    Clock clock = Clock.fixed(Instant.now(), ZoneId.of(ZoneOffset.UTC.getId()))

    // Mocks
    AbstractReactiveMongoRepository<MiniCactpotAggregate> repository
    MiniCactpotProperties properties

    // Target class
    MiniCactpotService service

    // misc
    Map<Integer, Integer> fedMap

    void setup() {
        repository = Mock()
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
            new MiniCactpotMapper(new RandomNumberGenerator()),
            clock
        )
    }

    def "get winnings map"() {
        when:
        def result = service.getWinningsMap().block()

        then:
        result == fedMap
    }

    def "start a new game"() {
        when:
        def result = service.startGame().block()

        then:
        1 * repository.insert(_) >> { MiniCactpotAggregate it -> Mono.just(it) }

        result.id() != null
        result.board().size() == 9
        result.stage() == SCRATCHING_FIRST
    }
}

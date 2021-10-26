package dc.vilnius.slack.domain

import spock.lang.Specification
import spock.lang.Unroll

import java.time.LocalDateTime
import java.time.Month

class HeroesOfTheMonthCommandSpec extends Specification {

    @Unroll
    def "Should reveal heroes at the end of the month: #dateTime"() {
        expect:
        HeroesOfTheMonthDateUtil.isAllowedToRevealHeroesLeaderboard(dateTime)

        where:
        dateTime << [
                LocalDateTime.of(2021, Month.SEPTEMBER, 26, 0, 0),
                LocalDateTime.of(2021, Month.SEPTEMBER, 27, 0, 0),
                LocalDateTime.of(2021, Month.SEPTEMBER, 28, 0, 0),
                LocalDateTime.of(2021, Month.SEPTEMBER, 29, 0, 0),
                LocalDateTime.of(2021, Month.SEPTEMBER, 30, 0, 0)
        ]
    }

    @Unroll
    def "Should not reveal heroes at the end of the month: #dateTime"() {
        expect:
        !HeroesOfTheMonthDateUtil.isAllowedToRevealHeroesLeaderboard(dateTime)

        where:
        dateTime << [
                LocalDateTime.of(2021, Month.SEPTEMBER, 1, 0, 0),
                LocalDateTime.of(2021, Month.SEPTEMBER, 25, 0, 0),
                LocalDateTime.of(2021, Month.OCTOBER, 1, 0, 0)
        ]
    }
}

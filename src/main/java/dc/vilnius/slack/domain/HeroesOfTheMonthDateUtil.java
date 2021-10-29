package dc.vilnius.slack.domain;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HeroesOfTheMonthDateUtil {

  private static final Logger logger = LoggerFactory.getLogger(HeroesOfTheMonthDateUtil.class);

  private HeroesOfTheMonthDateUtil() {}

  public static boolean isAllowedToRevealHeroesLeaderboard(LocalDate date) {
    logger.info("isAllowedToRevealHeroesLeaderboard({})", date);
    var lastDayOfMonth = date.with(TemporalAdjusters.lastDayOfMonth()).plusDays(1);
    var allowedDate = heroesLeaderBoardAvailableFrom(date);
    var isAllowed = date.isAfter(allowedDate) && date.isBefore(lastDayOfMonth);
    logger.info("isAllowedToRevealHeroesLeaderboard: {},  startDate: {}, lastDatOfMonth: {}", isAllowed, allowedDate, lastDayOfMonth);
    return isAllowed;
  }

  public static LocalDate heroesLeaderBoardAvailableFrom(LocalDate date) {
    return date.with(TemporalAdjusters.lastDayOfMonth()).minusDays(5);
  }
}

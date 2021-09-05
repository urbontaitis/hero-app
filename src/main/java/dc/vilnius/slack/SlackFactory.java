package dc.vilnius.slack;

import com.slack.api.bolt.App;
import com.slack.api.bolt.AppConfig;
import dc.vilnius.kudos.domain.KudosFacade;
import dc.vilnius.kudos.dto.GiveKudos;
import dc.vilnius.slack.domain.MessageGenerator;
import dc.vilnius.slack.domain.SlackMessageFacade;
import io.micronaut.context.annotation.Factory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Singleton;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.stream.Collectors;

@Factory
public class SlackFactory {

  private final Logger logger = LoggerFactory.getLogger(SlackFactory.class);

  @Singleton
  public AppConfig createAppConfig() {
    return new AppConfig();
  }

  @Singleton
  public App createApp(AppConfig appConfig, KudosFacade kudosFacade) {
    App app = new App(appConfig);
    var slackMessageFacade = new SlackMessageFacade(kudosFacade, appConfig);

    app.command("/hero-vote", (req, ctx) -> {
      var commandArgText = req.getPayload().getText();
      var channelId = req.getPayload().getChannelId();
      var userId = req.getPayload().getUserId();
      var parsedMessage = slackMessageFacade.parseMessage(commandArgText);
      if (parsedMessage.users().contains(userId)) {
        return ctx.ack("Really? No cheating mate!");
      }
      var kudos = new GiveKudos(channelId, parsedMessage.users(), parsedMessage.message());
      slackMessageFacade.handleHeroVote(kudos);
      var successMessages = parsedMessage.users().stream()
          .map(MessageGenerator::randomSuccessMessage).collect(
              Collectors.joining(";"));

      return ctx.ack(successMessages);
    });

    app.command("/heroes-of-the-month", (req, ctx) -> {
      var channelId = req.getPayload().getChannelId();
      var userId = req.getPayload().getUserId();
      logger.info("User {} in the channel {} requesting heroes!", userId, channelId);
      var currentTime = LocalDateTime.now();
      var lastDayOfMonth = currentTime.with(TemporalAdjusters.lastDayOfMonth());
      var allowedDate = currentTime.with(TemporalAdjusters.lastDayOfMonth()).minusDays(5);
      if (allowedDate.isBefore(currentTime) || lastDayOfMonth.isAfter(currentTime)) {
        return ctx.ack("It's too soon to reveal this month heroes!");
      } else {
        slackMessageFacade.handleHeroOfTheMonth(channelId);
        return ctx.ack("Working on it!");
      }
    });

    return app;
  }
}

package dc.vilnius.slack;

import com.slack.api.bolt.App;
import com.slack.api.bolt.AppConfig;
import dc.vilnius.kudos.dto.GiveKudos;
import dc.vilnius.slack.domain.SlackMessageFacade;
import io.micronaut.context.annotation.Factory;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.stream.Collectors;
import javax.inject.Inject;
import javax.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Factory
public class SlackFactory {

  private final Logger logger = LoggerFactory.getLogger(SlackFactory.class);
  private final SlackMessageFacade slackMessageFacade;

  @Inject
  public SlackFactory(SlackMessageFacade slackMessageFacade) {
    this.slackMessageFacade = slackMessageFacade;
  }

  @Singleton
  public AppConfig createAppConfig() {
    return new AppConfig();
  }

  @Singleton
  public App createApp(AppConfig config) {
    App app = new App(config);
    app.command("/hero-vote", (req, ctx) -> {
      var commandArgText = req.getPayload().getText();
      var channelId = req.getPayload().getChannelId();
//      var userId = req.getPayload().getUserId();
      var parsedMessage = slackMessageFacade.parseMessage(commandArgText);
//      Need to call slack api and get user
//      if (parsedMessage.usernames().contains(userId)) {
//        return ctx.ack("Really? No cheating mate!");
//      }
      slackMessageFacade.handleHeroVote(
          new GiveKudos(channelId, parsedMessage.usernames(), parsedMessage.message()));
      var successMessages = parsedMessage.usernames().stream()
          .map(SlackMessageFacade::randomSuccessMessage).collect(
              Collectors.joining(" ; "));

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
        return ctx.ack("It's too soon to reveal heroes!");
      } else {
        var message = slackMessageFacade.handleHeroOfTheMonth(channelId);
        ctx.respond(message);
        return ctx.ack("Looking for the heroes!");
      }
    });

    return app;
  }
}

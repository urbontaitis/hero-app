package dc.vilnius.slack.domain;

import dc.vilnius.kudos.domain.KudosFacade;
import dc.vilnius.kudos.dto.GiveKudos;
import dc.vilnius.slack.dto.SlackMessage;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class SlackMessageFacade {

  private static final List<String> SUCCESS_MESSAGES = Arrays.asList(
      "Thanks for voting 🥰 %s will be really happy 🥳🥳",
      "Oh Yeah! 🥳 %s is Rockstar this month 🤩",
      "%s will be really happy 🥰",
      "So %s is your hero this month, really? 🤔🤔",
      "%s will be really happy 🥳🥳"
  );

  private final KudosFacade kudosFacade;

  @Inject
  public SlackMessageFacade(KudosFacade kudosFacade) {
    this.kudosFacade = kudosFacade;
  }

  public SlackMessage parseMessage(String commandArgText) {
    return CommandParser.parse(commandArgText);
  }


  public void handleHeroVote(GiveKudos giveKudos) {
    kudosFacade.submit(giveKudos);
  }

  public String handleHeroOfTheMonth(String channelId) {
    return kudosFacade.findKudosBy(channelId).stream()
        .map(kudos -> kudos.username() + ": " + kudos.message())
        .collect(Collectors.joining(" ; "));
  }

  public static String randomSuccessMessage(String username) {
    var random = new Random();
    var messageIndex = random.ints(0, SUCCESS_MESSAGES.size() - 1).findFirst().orElseThrow();
    var messageTemplate = SUCCESS_MESSAGES.get(messageIndex);
    return String.format(messageTemplate, username);
  }
}

package dc.vilnius.post.domain;

import static java.util.stream.Collectors.toList;

import dc.vilnius.notification.ChannelResponse;
import dc.vilnius.notification.MattermostClient;
import java.time.LocalDateTime;
import java.util.List;
import javax.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Singleton
public class MessageFacade {

  private static final Logger logger = LoggerFactory.getLogger(MessageFacade.class);

  private final MessageCollector messageCollector;
  private final MessageSender messageSender;
  private final MattermostClient mattermostClient;

  public MessageFacade(MessageCollector messageCollector, MessageSender messageSender,
      MattermostClient mattermostClient) {
    this.messageCollector = messageCollector;
    this.messageSender = messageSender;
    this.mattermostClient = mattermostClient;
  }

  public List<ChannelResponse> sendComments(LocalDateTime dateTime) {
    var envelopes = messageCollector.collect(dateTime);
    var bot = mattermostClient.botDetails().blockingFirst();

    var messages = envelopes.stream().map(envelope -> {
      var receiver = mattermostClient.userDetailsByUsername(envelope.getUsername()).blockingFirst();
      return messageSender.sendMessage(bot.getId(), receiver.getId(), envelope.getMessage());
    }).collect(toList());

    logger.info("Total messages sent: {}", messages.size());
    return messages;
  }

}

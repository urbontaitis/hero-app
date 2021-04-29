package dc.vilnius.post.domain;

import dc.vilnius.notification.ChannelPost;
import dc.vilnius.notification.ChannelResponse;
import dc.vilnius.notification.MattermostClient;
import javax.inject.Singleton;

@Singleton
class MessageSender {

  private static final String BOT_MESSAGE_TEMPLATE = "Hey, I'm Hero bot, and this month votes for you are: \n %s";

  private final MattermostClient mattermostClient;

  public MessageSender(MattermostClient mattermostClient) {
    this.mattermostClient = mattermostClient;
  }

  public ChannelResponse sendMessage(String botId, String userId, String message) {
    var directChannel = mattermostClient.createChannelBetweenUsers(botId, userId).blockingGet();
    return send(directChannel.getId(), message);
  }

  private ChannelResponse send(String channelId, String message) {
    var botMessage = String.format(BOT_MESSAGE_TEMPLATE, message);
    return mattermostClient.send(new ChannelPost(channelId, botMessage)).blockingGet();
  }
}

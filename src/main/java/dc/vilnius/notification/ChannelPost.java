package dc.vilnius.notification;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ChannelPost {

  @JsonProperty("channel_id")
  private final String channelId;
  private final String message;

  public ChannelPost(String channelId, String message) {
    this.channelId = channelId;
    this.message = message;
  }

  public String getChannelId() {
    return channelId;
  }

  public String getMessage() {
    return message;
  }
}

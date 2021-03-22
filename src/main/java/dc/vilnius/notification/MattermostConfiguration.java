package dc.vilnius.notification;

import io.micronaut.context.annotation.ConfigurationProperties;
import io.micronaut.context.annotation.Requires;

@ConfigurationProperties(MattermostConfiguration.PREFIX)
@Requires(property = MattermostConfiguration.PREFIX)
public class MattermostConfiguration {
  public static final String PREFIX = "mattermost";

  private String botName;
  private String path;
  private String token;

  public String getBotName() {
    return botName;
  }

  public void setBotName(String botName) {
    this.botName = botName;
  }

  public String getPath() {
    return path;
  }

  public void setPath(String path) {
    this.path = path;
  }

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }
}

package dc.vilnius.notification;

import io.micronaut.core.type.Argument;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.client.RxHttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.http.uri.UriBuilder;
import io.reactivex.Flowable;
import io.reactivex.Maybe;
import java.util.Arrays;
import javax.inject.Singleton;

@Singleton
public class MattermostClient {

  private final RxHttpClient httpClient;
  private final MattermostConfiguration mattermostConfiguration ;

  public MattermostClient(@Client("${mattermost.url}") RxHttpClient httpClient, MattermostConfiguration mattermostConfiguration) {
    this.httpClient = httpClient;
    this.mattermostConfiguration = mattermostConfiguration;
  }

  public Flowable<UserDetails> botDetails() {
    return userDetailsByUsername(mattermostConfiguration.getBotName());
  }

  public Flowable<UserDetails> userDetailsByUsername(String username) {
    var uri = UriBuilder.of(mattermostConfiguration.getPath())
        .path("users/username")
        .path(username)
        .build();
    var request = HttpRequest.GET(uri);
    return httpClient.retrieve(request, Argument.of(UserDetails.class));
  }

  public Maybe<DirectChannel> createChannelBetweenUsers(String botId, String userId) {
    var uri = UriBuilder.of(mattermostConfiguration.getPath())
        .path("channels/direct")
        .build();
    var request = HttpRequest.POST(uri, Arrays.asList(botId, userId));
    return httpClient.retrieve(request, Argument.of(DirectChannel.class)).firstElement();
  }

  public Maybe<ChannelResponse> send(ChannelPost channelPost) {
    var uri = UriBuilder.of(mattermostConfiguration.getPath())
        .path("posts")
        .build();
    var request = HttpRequest.POST(uri, channelPost);
    return httpClient.retrieve(request, Argument.of(ChannelResponse.class)).firstElement();
  }
}

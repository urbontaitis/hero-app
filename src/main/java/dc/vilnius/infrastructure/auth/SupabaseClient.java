package dc.vilnius.infrastructure.auth;

import io.micronaut.core.type.Argument;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.client.RxHttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.http.uri.UriBuilder;
import io.reactivex.Flowable;
import java.net.URI;
import javax.inject.Singleton;

@Singleton
public class SupabaseClient {

  private final RxHttpClient httpClient;
  private final URI uri;

  public SupabaseClient(@Client("${supabase.url}") RxHttpClient httpClient, SupabaseConfiguration supabaseConfiguration) {
    this.httpClient = httpClient;
    this.uri = UriBuilder.of(supabaseConfiguration.getTokenEndpoint()).build();
  }

  Flowable<TokenResponse> fetchToken(LoginCommand loginCommand) {
    HttpRequest<?> request = HttpRequest.POST(uri, loginCommand);

    return httpClient.retrieve(request, Argument.of(TokenResponse.class));
  }
}

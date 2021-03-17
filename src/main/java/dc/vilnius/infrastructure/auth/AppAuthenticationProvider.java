package dc.vilnius.infrastructure.auth;

import edu.umd.cs.findbugs.annotations.Nullable;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.client.exceptions.HttpClientException;
import io.micronaut.security.authentication.AuthenticationException;
import io.micronaut.security.authentication.AuthenticationFailed;
import io.micronaut.security.authentication.AuthenticationProvider;
import io.micronaut.security.authentication.AuthenticationRequest;
import io.micronaut.security.authentication.AuthenticationResponse;
import io.micronaut.security.authentication.UserDetails;
import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import java.util.Collections;
import javax.inject.Inject;
import javax.inject.Singleton;
import org.reactivestreams.Publisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Singleton
class AppAuthenticationProvider implements AuthenticationProvider {

  private static final Logger logger = LoggerFactory.getLogger(AppAuthenticationProvider.class);

  private final SupabaseClient supabaseClient;

  @Inject
  AppAuthenticationProvider(SupabaseClient supabaseClient) {
    this.supabaseClient = supabaseClient;
  }

  @Override
  public Publisher<AuthenticationResponse> authenticate(@Nullable HttpRequest<?> httpRequest,
      AuthenticationRequest<?, ?> authenticationRequest) {
    return Flowable.create(emitter -> {
      var identity = (String) authenticationRequest.getIdentity();
      var secret = (String) authenticationRequest.getSecret();
      try {
        var flowable = supabaseClient.fetchToken(new LoginCommand(identity, secret));
        var response = flowable.blockingFirst();
        var role = response.getUser().getUserMetadata().get("role");
        emitter.onNext(new UserDetails(identity, Collections.singletonList(role)));
        emitter.onComplete();
      } catch (HttpClientException e) {
        logger.warn("Failed identity verification for {}. Cause: {}", identity, e.getMessage());
        emitter.onError(new AuthenticationException(new AuthenticationFailed()));
      }
    }, BackpressureStrategy.ERROR);
  }
}

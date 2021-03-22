package dc.vilnius.notification;

import io.micronaut.context.annotation.Requires;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MutableHttpRequest;
import io.micronaut.http.annotation.Filter;
import io.micronaut.http.filter.ClientFilterChain;
import io.micronaut.http.filter.HttpClientFilter;
import org.reactivestreams.Publisher;

@Filter("/api/v4/**")
@Requires(property = MattermostConfiguration.PREFIX + ".token")
public class MattermostFilter implements HttpClientFilter {

  private final MattermostConfiguration mattermostConfiguration;

  public MattermostFilter(MattermostConfiguration mattermostConfiguration) {
    this.mattermostConfiguration = mattermostConfiguration;
  }

  @Override
  public Publisher<? extends HttpResponse<?>> doFilter(MutableHttpRequest<?> request, ClientFilterChain chain) {
    return chain.proceed(request.bearerAuth(mattermostConfiguration.getToken()));
  }
}

package dc.vilnius.infrastructure.auth;

import io.micronaut.context.annotation.Requires;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MutableHttpRequest;
import io.micronaut.http.annotation.Filter;
import io.micronaut.http.filter.ClientFilterChain;
import io.micronaut.http.filter.HttpClientFilter;
import org.reactivestreams.Publisher;

@Filter("/auth/v1/**")
@Requires(property = SupabaseConfiguration.PREFIX + ".api-key")
public class SupabaseFilter implements HttpClientFilter {

  private final SupabaseConfiguration configuration;

  public SupabaseFilter(SupabaseConfiguration configuration) {
    this.configuration = configuration;
  }

  @Override
  public Publisher<? extends HttpResponse<?>> doFilter(MutableHttpRequest<?> request, ClientFilterChain chain) {
    return chain.proceed(request.header("apiKey", configuration.getApiKey()));
  }
}

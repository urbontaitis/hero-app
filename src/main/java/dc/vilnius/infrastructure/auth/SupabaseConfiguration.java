package dc.vilnius.infrastructure.auth;

import io.micronaut.context.annotation.ConfigurationProperties;
import io.micronaut.context.annotation.Requires;

@ConfigurationProperties(SupabaseConfiguration.PREFIX)
@Requires(property = SupabaseConfiguration.PREFIX)
public class SupabaseConfiguration {
  public static final String PREFIX = "supabase";

  private String apiKey;
  private String tokenEndpoint;

  public String getApiKey() {
    return apiKey;
  }

  public void setApiKey(String apiKey) {
    this.apiKey = apiKey;
  }

  public String getTokenEndpoint() {
    return tokenEndpoint;
  }

  public void setTokenEndpoint(String tokenEndpoint) {
    this.tokenEndpoint = tokenEndpoint;
  }
}

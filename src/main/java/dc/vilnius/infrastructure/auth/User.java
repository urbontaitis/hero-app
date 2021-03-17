package dc.vilnius.infrastructure.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Map;

public class User {

  private String id;

  @JsonProperty("user_metadata")
  private Map<String, String> userMetadata;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public Map<String, String> getUserMetadata() {
    return userMetadata;
  }

  public void setUserMetadata(Map<String, String> userMetadata) {
    this.userMetadata = userMetadata;
  }
}

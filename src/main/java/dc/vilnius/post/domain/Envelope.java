package dc.vilnius.post.domain;

import java.util.List;

class Envelope {

  private final String username;
  private final String message;

  Envelope(String username, List<String> messages) {
    this.username = username;
    this.message = String.join("\n", messages);
  }

  public String getUsername() {
    return username;
  }

  public String getMessage() {
    return message;
  }
}

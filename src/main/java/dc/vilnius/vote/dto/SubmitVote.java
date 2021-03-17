package dc.vilnius.vote.dto;

import javax.validation.constraints.NotBlank;

public class SubmitVote {

  @NotBlank
  private String email;

  @NotBlank
  private String comment;

  public SubmitVote() {}

  public SubmitVote(String email, String comment) {
    this.email = email;
    this.comment = comment;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getComment() {
    return comment;
  }

  public void setComment(String comment) {
    this.comment = comment;
  }
}

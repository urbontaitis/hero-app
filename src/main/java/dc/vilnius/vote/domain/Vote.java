package dc.vilnius.vote.domain;

import io.micronaut.data.annotation.DateCreated;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
public class Vote {

  @Id
  @GeneratedValue
  private UUID id;

  @NotNull
  private String email;

  @NotNull
  private String comment;

  @DateCreated
  private LocalDateTime createDate;

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
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

  public LocalDateTime getCreateDate() {
    return createDate;
  }

  public void setCreateDate(LocalDateTime createDate) {
    this.createDate = createDate;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Vote)) {
      return false;
    }
    Vote vote = (Vote) o;
    return getId().equals(vote.getId()) && getEmail().equals(vote.getEmail()) && getComment()
        .equals(vote.getComment()) && getCreateDate().equals(vote.getCreateDate());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getId(), getEmail(), getComment(), getCreateDate());
  }

  @Override
  public String toString() {
    return "Vote{" +
        "id=" + id +
        ", email='" + email + '\'' +
        ", comment='" + comment + '\'' +
        ", createDate=" + createDate +
        '}';
  }
}

package dc.vilnius.post;

import dc.vilnius.infrastructure.auth.AppRoles;
import dc.vilnius.notification.ChannelResponse;
import dc.vilnius.post.domain.MessageFacade;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Post;
import io.micronaut.security.annotation.Secured;
import java.time.LocalDateTime;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Controller("/post")
public class PostController {

  private final MessageFacade messageFacade;

  public PostController(MessageFacade messageFacade) {
    this.messageFacade = messageFacade;
  }

  @Secured({AppRoles.APP_ADMIN_ROLE})
  @Post(produces = MediaType.APPLICATION_JSON, consumes = MediaType.APPLICATION_JSON)
  List<ChannelResponse> notifyHeroes(@Valid @NotNull LocalDateTime dateTime) {
    return messageFacade.sendComments(dateTime);
  }
}

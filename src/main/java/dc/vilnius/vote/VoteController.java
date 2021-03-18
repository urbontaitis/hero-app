package dc.vilnius.vote;

import dc.vilnius.infrastructure.auth.AppRoles;
import dc.vilnius.vote.domain.Vote;
import dc.vilnius.vote.domain.VoteFacade;
import dc.vilnius.vote.dto.SubmitVote;
import edu.umd.cs.findbugs.annotations.Nullable;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;
import io.micronaut.validation.Validated;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import javax.inject.Inject;
import javax.validation.Valid;

@Controller("/votes")
@Secured(SecurityRule.IS_AUTHENTICATED)
@Validated
class VoteController {

  private final VoteFacade voteFacade;

  @Inject
  VoteController(VoteFacade voteFacade) {
    this.voteFacade = voteFacade;
  }

  @Post(produces = MediaType.APPLICATION_JSON, consumes = MediaType.APPLICATION_JSON)
  Vote submitVote(@Valid SubmitVote submitVote) {
    return voteFacade.submit(submitVote);
  }

  @Secured({AppRoles.APP_ADMIN_ROLE})
  @Get(value = "{?date}", produces = MediaType.APPLICATION_JSON)
  List<Vote> votes(@Nullable String date) {
    var dateTime = Optional.ofNullable(date)
        .map(LocalDate::parse)
        .map(LocalDate::atStartOfDay)
        .orElse(LocalDateTime.now());
    return voteFacade.findAllByMonth(dateTime);
  }

}

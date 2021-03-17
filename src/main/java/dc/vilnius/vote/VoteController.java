package dc.vilnius.vote;

import dc.vilnius.vote.domain.Vote;
import dc.vilnius.vote.domain.VoteFacade;
import dc.vilnius.vote.dto.SubmitVote;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Post;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;
import io.micronaut.validation.Validated;
import javax.inject.Inject;
import javax.validation.Valid;

@Controller("/votes")
@Secured(SecurityRule.IS_ANONYMOUS)
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

}

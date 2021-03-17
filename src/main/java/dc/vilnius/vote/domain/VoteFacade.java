package dc.vilnius.vote.domain;

import dc.vilnius.vote.dto.SubmitVote;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class VoteFacade {

  private final VoteRepository voteRepository;

  @Inject
  public VoteFacade(VoteRepository voteRepository) {
    this.voteRepository = voteRepository;
  }

  public Vote submit(SubmitVote submitVote) {
    var vote = new Vote();
    vote.setEmail(submitVote.getEmail());
    vote.setComment(submitVote.getComment());
    return voteRepository.save(vote);
  }
}

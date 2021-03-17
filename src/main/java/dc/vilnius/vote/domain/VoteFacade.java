package dc.vilnius.vote.domain;

import static java.util.stream.Collectors.toList;

import dc.vilnius.vote.dto.SubmitVote;
import java.util.List;
import java.util.stream.StreamSupport;
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

  public List<Vote> findAll() {
    var votes = voteRepository.findAll();
    return StreamSupport.stream(votes.spliterator(), false).collect(toList());
  }
}

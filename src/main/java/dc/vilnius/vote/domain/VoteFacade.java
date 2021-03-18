package dc.vilnius.vote.domain;

import dc.vilnius.vote.dto.SubmitVote;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
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

  public List<Vote> findAllByMonth(LocalDateTime dateTime) {
    var start = dateTime.with(TemporalAdjusters.firstDayOfMonth());
    var end = dateTime.with(TemporalAdjusters.lastDayOfMonth());
    return voteRepository.findByCreateDateBetweenOrderByEmail(start, end);
  }
}

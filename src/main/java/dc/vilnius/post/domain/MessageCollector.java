package dc.vilnius.post.domain;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;

import dc.vilnius.hero.domain.HeroFacade;
import dc.vilnius.vote.domain.Vote;
import dc.vilnius.vote.domain.VoteFacade;
import java.time.LocalDateTime;
import java.util.List;
import javax.inject.Singleton;

@Singleton
class MessageCollector {

  private final HeroFacade heroFacade;
  private final VoteFacade voteFacade;

  public MessageCollector(HeroFacade heroFacade, VoteFacade voteFacade) {
    this.heroFacade = heroFacade;
    this.voteFacade = voteFacade;
  }

  private Envelope transform(String email, List<Vote> votes) {
    var username = heroFacade.findByEmail(email).getUsername();
    var messages = votes.stream().map(v -> String.format("* %s", v.getComment())).collect(toList());
    return new Envelope(username, messages);
  }

  public List<Envelope> collect(LocalDateTime dateTime) {
    var receiversAndTheirVotes = voteFacade.findAllByMonth(dateTime)
        .stream()
        .collect(groupingBy(Vote::getEmail, toList()));
    return receiversAndTheirVotes.keySet()
        .stream()
        .map(receiver -> transform(receiver, receiversAndTheirVotes.get(receiver)))
        .collect(toList());
  }

}

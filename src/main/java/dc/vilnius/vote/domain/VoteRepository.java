package dc.vilnius.vote.domain;

import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.CrudRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
interface VoteRepository extends CrudRepository<Vote, UUID> {

  List<Vote> findByCreateDateBetweenOrderByEmail(LocalDateTime startDate, LocalDateTime endDate);

}

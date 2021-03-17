package dc.vilnius.hero.domain;

import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.CrudRepository;
import java.util.UUID;
import javax.validation.constraints.NotNull;

@Repository
interface HeroRepository extends CrudRepository<Hero, UUID> {

  void deleteByUsername(@NotNull String username);
}

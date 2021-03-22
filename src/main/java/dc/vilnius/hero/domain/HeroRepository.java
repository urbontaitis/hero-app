package dc.vilnius.hero.domain;

import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.CrudRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import javax.validation.constraints.NotNull;

@Repository
interface HeroRepository extends CrudRepository<Hero, UUID> {

  List<Hero> findAllOrderByLastName();

  void deleteByUsername(@NotNull String username);

  Optional<Hero> findByEmail(@NotNull String email);
}

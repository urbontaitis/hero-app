package dc.vilnius.hero.domain;

import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.CrudRepository;
import java.util.UUID;

@Repository
interface HeroRepository extends CrudRepository<Hero, UUID> {

}

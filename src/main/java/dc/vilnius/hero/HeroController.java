package dc.vilnius.hero;

import dc.vilnius.hero.domain.Hero;
import dc.vilnius.hero.domain.HeroFacade;
import dc.vilnius.infrastructure.auth.AppRoles;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;
import io.micronaut.validation.Validated;
import java.util.List;
import javax.inject.Inject;
import javax.validation.Valid;

@Controller("/heroes")
@Secured(SecurityRule.IS_AUTHENTICATED)
@Validated
class HeroController {

  private final HeroFacade heroFacade;

  @Inject
  HeroController(HeroFacade heroFacade) {
    this.heroFacade = heroFacade;
  }

  @Get(produces = MediaType.APPLICATION_JSON)
  List<Hero> allHeroes() {
    return heroFacade.heroes();
  }

  @Secured({AppRoles.APP_ADMIN_ROLE})
  @Post(produces = MediaType.APPLICATION_JSON, consumes = MediaType.APPLICATION_JSON)
  Hero save(@Valid Hero hero) {
    return heroFacade.save(hero);
  }

}

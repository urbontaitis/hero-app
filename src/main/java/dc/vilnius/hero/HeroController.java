package dc.vilnius.hero;

import dc.vilnius.hero.domain.Hero;
import dc.vilnius.hero.domain.HeroFacade;
import dc.vilnius.hero.dto.CreateHeroes;
import dc.vilnius.hero.dto.DeleteHero;
import dc.vilnius.hero.dto.HeroNotFound;
import dc.vilnius.infrastructure.auth.AppRoles;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Delete;
import io.micronaut.http.annotation.Error;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.annotation.Put;
import io.micronaut.http.hateoas.JsonError;
import io.micronaut.http.hateoas.Link;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;
import io.micronaut.validation.Validated;
import java.util.List;
import javax.inject.Inject;
import javax.validation.Valid;

@Controller(value = "/heroes")
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
  @Put(produces = MediaType.APPLICATION_JSON, consumes = MediaType.APPLICATION_JSON)
  Hero save(@Valid Hero hero) {
    return heroFacade.save(hero);
  }

  @Secured({AppRoles.APP_ADMIN_ROLE})
  @Post(produces = MediaType.APPLICATION_JSON, consumes = MediaType.APPLICATION_JSON)
  List<Hero> saveAll(@Valid CreateHeroes createHeroes) {
    return heroFacade.save(createHeroes.getHeroes());
  }

  @Secured({AppRoles.APP_ADMIN_ROLE})
  @Delete(consumes = MediaType.APPLICATION_JSON)
  void delete(@Valid DeleteHero deleteHero) {
    heroFacade.delete(deleteHero);
  }

  @Error(value = HeroNotFound.class, global = true)
  public HttpResponse<JsonError> handle(HttpRequest request, HeroNotFound e) {
    JsonError error = new JsonError(e.getMessage())
        .link(Link.SELF, Link.of(request.getUri()));
    return HttpResponse.<JsonError>notFound().body(error);
  }
}

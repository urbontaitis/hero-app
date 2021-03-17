package dc.vilnius.hero.dto;

import dc.vilnius.hero.domain.Hero;
import io.micronaut.validation.Validated;
import java.util.List;
import javax.validation.constraints.NotNull;

@Validated
public class CreateHeroes {

  @NotNull
  private List<Hero> heroes;

  public List<Hero> getHeroes() {
    return heroes;
  }

  public void setHeroes(List<Hero> heroes) {
    this.heroes = heroes;
  }
}

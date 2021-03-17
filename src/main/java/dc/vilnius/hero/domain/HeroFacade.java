package dc.vilnius.hero.domain;

import static java.util.stream.Collectors.toList;

import dc.vilnius.hero.dto.DeleteHero;
import java.util.List;
import java.util.stream.StreamSupport;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class HeroFacade {

  private final HeroRepository heroRepository;

  @Inject
  public HeroFacade(HeroRepository heroRepository) {
    this.heroRepository = heroRepository;
  }

  public List<Hero> heroes() {
    var heroes = heroRepository.findAll();
    return StreamSupport.stream(heroes.spliterator(), false).collect(toList());
  }

  public Hero save(Hero hero) {
    return heroRepository.save(hero);
  }

  public List<Hero> save(List<Hero> heroes) {
    var saved = heroRepository.saveAll(heroes);
    return StreamSupport.stream(saved.spliterator(), false).collect(toList());
  }

  public void delete(DeleteHero deleteHero) {
    heroRepository.deleteByUsername(deleteHero.getUsername());
  }
}

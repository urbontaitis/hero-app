package dc.vilnius.hero.dto;

public class HeroNotFound extends RuntimeException {

  public HeroNotFound(String email) {
    super("Hero by " + email + " does not exist.");
  }
}

package dc.vilnius;

import io.micronaut.runtime.Micronaut;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;

@OpenAPIDefinition(
    info = @Info(
        title = "Hero app",
        version = "0.0.1",
        description = "Hero API",
        license = @License(name = "MIT", url = "https://github.com/urbontaitis/hero-app/blob/main/LICENSE")
    )
)
public class Application {

    public static void main(String[] args) {
        Micronaut.build(args)
            .banner(false)
            .start();
    }
}

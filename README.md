# Hero API

## Deployment to Heroku

Login into heroku
```bash
heroku login
heroku container:login
```

Build and deploy container
```bash
./gradlew stage
heroku container:push web
heroku container:release web
```

## Micronaut 2.4.0 Documentation

- [User Guide](https://docs.micronaut.io/2.4.0/guide/index.html)
- [API Reference](https://docs.micronaut.io/2.4.0/api/index.html)
- [Configuration Reference](https://docs.micronaut.io/2.4.0/guide/configurationreference.html)
- [Micronaut Guides](https://guides.micronaut.io/index.html)
---

## Feature reactor documentation

- [Micronaut Reactor documentation](https://micronaut-projects.github.io/micronaut-reactor/snapshot/guide/index.html)

## Feature hibernate-validator documentation

- [Micronaut Hibernate Validator documentation](https://micronaut-projects.github.io/micronaut-hibernate-validator/latest/guide/index.html)

## Feature security documentation

- [Micronaut Security documentation](https://micronaut-projects.github.io/micronaut-security/latest/guide/index.html)

## Feature http-client documentation

- [Micronaut HTTP Client documentation](https://docs.micronaut.io/latest/guide/index.html#httpClient)


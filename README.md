## Technological stack

1. JPA (Hibernate) as a persistence layer
1. Spring shell as cli


## Quick start
1. Run `mvn spring-boot:run` to quick start this project.

Now you can use CLI or web-uo on http://localhost:9090/ui.html

![commands](/commands.jpeg)


## What should be done?

1. Add functionality, that allows to pay for specified client debt.
1. Each payment should be written into Database as a separate record (you'll need new Entity for that)
1. Client debt shown by `show-all-clients` should be decreased accordingly.
1. Make each cli command available through web (rest) point (Spring web).
1. When you're done you should commit and push your changes.

## Additional tasks

1. Database creation through flyway or liquibase (now it's done through hibernate autoddl)
1. It would be great if you'll make web client (ui) for rest points you've made previously.

## License
This project is licensed under the MIT license.



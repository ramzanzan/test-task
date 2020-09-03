
## Prerequisites

1. Maven
1. Java 8

## Technological stack

1. JPA (Hibernate) as a persistence layer
1. Spring shell as cli


## Quick start

1. Clone this repo to your computer.
1. Run `mvn spring-boot:run` to quick start this project.

This project should be easily picked up by your favorite IDE (as it only depends on Maven as a build tool).
Now you're ready to rumble!


![commands](/commands.jpeg)


## What should be done?

1. Add functionality, that allows to pay for specified client debt.
1. Each payment should be written into Database as a separate record (you'll need new Entity for that)
1. Client debt shown by `show-all-clients` should be decreased accordingly.
1. Make each cli command available through web (rest) point (Spring web).
1. When you're done you should commit and push your changes.

## Additional tasks

1. Write some unit test
1. Database creation through flyway or liquibase (now it's done through hibernate autoddl)
1. It would be great if you'll make web client (ui) for rest points you've made previously.

## License

This project is licensed under the MIT license.

Hi all, welcome to my mickey mouse SpringBoot project

Where I implement all my learnings in my own example

Obviously I am very passionate about spring and very bored (aka dopamine junkie)

Things on here

Dev:
1. 2 spring boot microservices
2. One microservice has 4 spring boot api (in proper spring mvc format)
3. Another one is for authentication and fraud check, just returns false for now
4. Docker, docker compose, docker db
5. Mvn build package with jib
6. Connect to aws secretsmanager
7. Connect to aws sqs pub/sub
8. Connect to AWS RDS
9. Added dumb connection between 2 microservices

Test:

1. Unit tests
2. Mockito
3. E2E integration tests (follows TDD)
4. Used test containers (mock mvc, mock bean)

TODO:
1. Connect 2 microservices
2. Some kinda kafka (which is similar to sqs)
3. some angular 
4. some kubernetes

Useful commands:

1. docker-compose up -d
2. docker-compose down
3. docker compose ps
4. mvn clean compile jib:build
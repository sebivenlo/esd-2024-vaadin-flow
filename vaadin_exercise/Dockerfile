FROM openjdk:17-jdk-slim

WORKDIR /app

COPY pom.xml .
COPY src ./src

RUN apt-get update && apt-get install -y maven
RUN mvn dependency:go-offline

ENV SPRING_DEVTOOLS_RESTART_ENABLED=true
ENV SPRING_DEVTOOLS_ADD_PROPERTIES=true

EXPOSE 8080

CMD ["mvn", "spring-boot:run"]
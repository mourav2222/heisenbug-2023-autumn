FROM eclipse-temurin:21-jdk

WORKDIR /rococo
COPY ./gradle ./gradle
COPY ./rococo-e2e ./rococo-e2e
COPY ./gradlew ./
COPY ./build.gradle ./
COPY ./settings.gradle ./
COPY ./gradle.properties ./

CMD ./gradlew clean test -Dtest.env=docker

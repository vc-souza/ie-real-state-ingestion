ARG BASE_MAVEN=maven:3.9.11-amazoncorretto-21
ARG BASE_JDK=amazoncorretto:21

FROM ${BASE_MAVEN} AS dependencies

WORKDIR /app

COPY pom.xml pom.xml

RUN mvn -B -e org.apache.maven.plugins:maven-dependency-plugin:3.9.0:go-offline

FROM ${BASE_MAVEN} AS builder

WORKDIR /app

COPY --from=dependencies /root/.m2 /root/.m2
COPY --from=dependencies /app /app
COPY src /app/src

RUN mvn -B -e clean install -DskipTests

FROM ${BASE_JDK}

WORKDIR /app

COPY --from=builder /app/target/*.jar /app/app.jar

EXPOSE 8080

VOLUME [ "/app/config" ]

ENTRYPOINT [ "java", "-jar", "/app/app.jar" ]

FROM maven:3.5-jdk-8 as BUILD

ADD pom.xml /tmp
ADD server-side-rendering/pom.xml /tmp/server-side-rendering/
RUN mvn -f /tmp/server-side-rendering/pom.xml verify clean --fail-never

ADD server-side-rendering/src /tmp/server-side-rendering/src
RUN mvn -f /tmp/server-side-rendering/pom.xml verify

FROM frolvlad/alpine-oraclejdk8:slim

COPY --from=BUILD /tmp/server-side-rendering/target/*.jar /app.jar

ENTRYPOINT ["java","-jar","/app.jar"]
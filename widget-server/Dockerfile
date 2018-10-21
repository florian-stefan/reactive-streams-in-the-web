FROM maven:3.5-jdk-8 as BUILD

ADD pom.xml /tmp
ADD widget-server/pom.xml /tmp/widget-server/
RUN mvn -f /tmp/widget-server/pom.xml verify clean --fail-never

ADD widget-server/src /tmp/widget-server/src
RUN mvn -f /tmp/widget-server/pom.xml verify

FROM frolvlad/alpine-oraclejdk8:slim

COPY --from=BUILD /tmp/widget-server/target/*.jar /app.jar

ENTRYPOINT ["java","-jar","/app.jar"]
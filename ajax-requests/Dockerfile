FROM maven:3.5-jdk-8 as BUILD

ADD pom.xml /tmp
ADD ajax-requests/pom.xml /tmp/ajax-requests/
RUN mvn -f /tmp/ajax-requests/pom.xml verify clean --fail-never

ADD ajax-requests/src /tmp/ajax-requests/src
RUN mvn -f /tmp/ajax-requests/pom.xml verify

FROM frolvlad/alpine-oraclejdk8:slim

COPY --from=BUILD /tmp/ajax-requests/target/*.jar /app.jar

ENTRYPOINT ["java","-jar","/app.jar"]
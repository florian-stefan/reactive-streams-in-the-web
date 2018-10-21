FROM maven:3.5-jdk-8 as BUILD

ADD pom.xml /tmp
ADD progressive-html-rendering/pom.xml /tmp/progressive-html-rendering/
RUN mvn -f /tmp/progressive-html-rendering/pom.xml verify clean --fail-never

ADD progressive-html-rendering/src /tmp/progressive-html-rendering/src
RUN mvn -f /tmp/progressive-html-rendering/pom.xml verify

FROM frolvlad/alpine-oraclejdk8:slim

COPY --from=BUILD /tmp/progressive-html-rendering/target/*.jar /app.jar

ENTRYPOINT ["java","-jar","/app.jar"]
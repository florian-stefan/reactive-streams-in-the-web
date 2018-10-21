FROM maven:3.5-jdk-8 as BUILD

ADD pom.xml /tmp
ADD ajax-requests-with-http-2/pom.xml /tmp/ajax-requests-with-http-2/
RUN mvn -f /tmp/ajax-requests-with-http-2/pom.xml verify clean --fail-never

ADD ajax-requests-with-http-2/src /tmp/ajax-requests-with-http-2/src
RUN mvn -f /tmp/ajax-requests-with-http-2/pom.xml verify

FROM frolvlad/alpine-oraclejdk8:slim

COPY --from=BUILD /tmp/ajax-requests-with-http-2/target/*.jar /app.jar
COPY --from=BUILD /root/.m2/repository/org/mortbay/jetty/alpn/jetty-alpn-agent/2.0.6/jetty-alpn-agent-2.0.6.jar /alpn-agent.jar

ENTRYPOINT ["java","-jar","/app.jar","-Xbootclasspath/p:alpn-agent.jar"]
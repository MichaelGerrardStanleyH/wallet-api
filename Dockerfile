FROM openjdk:slim-buster
ARG JAR_FILE=target/wallet-api-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
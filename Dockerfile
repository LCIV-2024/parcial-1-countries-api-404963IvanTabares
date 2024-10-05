FROM openjdk:17-jdk-alpine
COPY ./target/*.jar county-app.jar
ENTRYPOINT ["java","-jar","country-app.jar"]
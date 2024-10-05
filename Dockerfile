FROM openjdk:17-jdk-alpine
COPY ./target/*.jar country-app.jar
ENTRYPOINT ["java","-jar","country-app.jar"]
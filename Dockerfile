FROM openjdk:8-jdk-alpine
ADD target/spring-boot-gym-api.jar app.jar
EXPOSE 8443
ENTRYPOINT ["java","-jar","/app.jar"]
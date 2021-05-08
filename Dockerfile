From openjdk:8
copy ./target/api-1.jar api-1.jar
CMD ["java","-jar","api-1.jar"]
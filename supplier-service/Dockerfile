FROM openjdk:8-jdk-alpine
ADD target/supplier-service.jar supplier.jar
EXPOSE 8082
ENTRYPOINT ["java","-jar","supplier.jar"]
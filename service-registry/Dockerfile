FROM openjdk:8-jdk-alpine
ADD target/service-registry.jar service_registry.jar
EXPOSE 8302
ENTRYPOINT ["java","-jar","service_registry.jar"]
FROM openjdk:8-jdk-alpine
ADD target/hystrix-dashboard-service.jar hystrix-dashboard-service.jar
EXPOSE 8085
ENTRYPOINT ["java","-jar","hystrix-dashboard-service.jar"]
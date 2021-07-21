FROM openjdk:8-jdk-alpine
COPY target/order-service.jar order.jar
EXPOSE 8083
ENTRYPOINT ["java","-jar","order.jar"]
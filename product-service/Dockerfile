FROM openjdk:8-jdk-alpine
ADD target/product-service.jar /product.jar
EXPOSE 8081
ENTRYPOINT ["java","-jar","product.jar"]

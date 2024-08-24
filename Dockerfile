FROM openjdk:17-jdk-alpine
WORKDIR /app
COPY target/product-service*.jar /app/product-service.jar
CMD ["java", "-jar", "product-service.jar"]
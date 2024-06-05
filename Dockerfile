# Etapa de construcción de la aplicación
FROM maven:3.8.4-openjdk-17 AS build
COPY . /app
WORKDIR /app
RUN mvn clean package

# Etapa final de la imagen
FROM openjdk:17-oracle
COPY --from=build /app/target/*.jar /demo.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "demo.jar"]

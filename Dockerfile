# Step 1: Use an official Maven image with Java 21 fully pre-configured
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app

# Copy the build configuration file
COPY pom.xml .

# Copy your source folder
COPY src ./src

# Compile and package your .jar file directly using Java 21
RUN mvn clean package -DskipTests

# Step 2: Use matching optimized Java 21 runtime environment
FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
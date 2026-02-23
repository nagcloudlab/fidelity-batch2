# =========================================================
# Dockerfile for Simple Bank Application
# Packages the Spring Boot executable JAR into a container
# =========================================================

# Step 1: Start from a base image that has Java 17 (Alpine = small footprint)
FROM eclipse-temurin:17-jdk-alpine

# Step 2: Set the working directory inside the container
WORKDIR /app

# Step 3: Copy the Spring Boot executable JAR into the container
COPY target/banking-app-web-0.0.1-SNAPSHOT.jar app.jar

# Step 4: Document which port the application listens on
EXPOSE 8080

# Step 5: Command to run when the container starts
CMD ["java", "-jar", "app.jar"]

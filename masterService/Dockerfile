# Use an official OpenJDK runtime as a parent image
FROM openjdk:17-jdk-slim

# Copy the pre-built JAR file into the container
COPY target/masterservice-0.0.1-SNAPSHOT.jar .

# Specify the command to run on container start
CMD ["java", "-jar", "masterservice-0.0.1-SNAPSHOT.jar"]


FROM maven:3.9.5-eclipse-temurin-17 AS builder

WORKDIR /app


COPY pom.xml .
COPY src ./src


RUN mvn clean package -DskipTests


FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app


COPY --from=builder /app/target/*.jar bot.jar


CMD ["java", "-jar", "bot.jar"]
FROM maven:3.9.6-eclipse-temurin-17 AS build

WORKDIR /astra
COPY . .
RUN mvn clean package -DskipTests

FROM eclipse-temurin:17-jdk

WORKDIR /astra

EXPOSE 8080

COPY --from=build /astra/target/Astra-Bot-1.0.jar Astra-Bot.jar

ENTRYPOINT ["java", "-jar", "Astra-Bot.jar"]

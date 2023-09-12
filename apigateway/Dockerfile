FROM maven:3.8.4-openjdk-17-slim AS  builder

WORKDIR /app
COPY . .

RUN mvn clean install

FROM openjdk:17-alpine

COPY --from=builder /app/target/*.jar /app.jar

EXPOSE 8765

ENTRYPOINT ["java", "-jar", "/app.jar"]

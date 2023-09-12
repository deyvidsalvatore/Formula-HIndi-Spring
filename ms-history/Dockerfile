FROM maven:3.8.4-openjdk-17-slim AS  builder

WORKDIR /app
COPY . .

RUN mvn clean package -DskipTests=true

FROM openjdk:17-alpine

COPY --from=builder /app/target/*.jar /app.jar

EXPOSE 8000

ENTRYPOINT ["java", "-jar", "/app.jar"]

CMD ["sh", "-c", "while ! nc -z mongodb-database 27017; do sleep 1; done && java -jar /app.jar"]
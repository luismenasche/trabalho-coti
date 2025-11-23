FROM maven:3.9.11-amazoncorretto-21-debian AS builder

WORKDIR /app

COPY . .

RUN mvn clean package -DskipTests

FROM amazoncorretto:21

WORKDIR /app

COPY --from=builder /app/target/*.jar clientes-api.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "clientes-api.jar"]
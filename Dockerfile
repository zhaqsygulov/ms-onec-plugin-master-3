FROM maven:3.8.5-openjdk-17 as build
WORKDIR /app
COPY . .
RUN ./mvnw clean package -DskipTests

FROM openjdk:17-jdk-alpine
WORKDIR /app
COPY --from=build /app/target/widget-0.0.1-SNAPSHOT.jar /app/app.jar
EXPOSE 8080
ENV TZ="Asia/Almaty"
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/app/app.jar"]

FROM openjdk:17-jdk-alpine
WORKDIR /app
COPY /target/*.jar /app/app.jar
EXPOSE 8080
ENV TZ="Asia/Almaty"
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app/app.jar"]
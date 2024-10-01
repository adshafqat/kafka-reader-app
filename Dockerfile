FROM adoptopenjdk:11-jdk-hotspot

WORKDIR /app

COPY target/kafka-reader-app-1.0-jar-with-dependencies.jar app.jar

CMD ["java", "-jar", "app.jar"]

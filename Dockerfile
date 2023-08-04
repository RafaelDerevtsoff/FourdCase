FROM openjdk:17-alpine

WORKDIR /app

COPY build/libs/case-0.0.1-SNAPSHOT.jar /app/case.jar

ENV POSTGRES_HOST=localhost RABBIT_HOST=rabbitmq

CMD ["java", "-jar", "case.jar"]
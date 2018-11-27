FROM openjdk:11-jre-slim

RUN mkdir /app

WORKDIR /app

ADD ./api/target/storage-api-1.0.0.jar /app

EXPOSE 8083

CMD java -jar storage-api-1.0.0.jar
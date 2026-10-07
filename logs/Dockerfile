FROM eclipse-temurin:17

WORKDIR /app

COPY src/Logger.java .

RUN javac Logger.java

RUN mkdir -p /logs

VOLUME /logs

ENTRYPOINT ["java", "Logger"]
FROM maven:3.9-eclipse-temurin-17
 
WORKDIR /app
 
ADD pom.xml .
ADD src ./src
 
RUN mvn clean package
 
CMD ["java", "-jar", "target/demo-0.0.1-SNAPSHOT.jar"]
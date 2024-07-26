FROM maven:3-eclipse-temurin-17-alpine

WORKDIR /usr/src/java-code
COPY . /usr/src/java-code/

RUN ls
RUN mvn -e package -DskipTests

WORKDIR /usr/src/java-app
RUN cp /usr/src/java-code/target/*.jar ./app.jar

EXPOSE 8085

CMD ["java", "-jar", "app.jar"]


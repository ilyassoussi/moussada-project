#create build
FROM maven:3.8.5-openjdk-17-slim AS stage1
WORKDIR /home/app
COPY . /home/app/
RUN mvn -f /home/app/pom.xml clean package  -DskipTests


# Create an Image
FROM openjdk:17-jdk-alpine
EXPOSE 8090
COPY --from=stage1 /home/app/target/*.jar shared.jar
CMD java -jar /shared.jar
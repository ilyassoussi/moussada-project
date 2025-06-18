#create build
FROM maven:3.8.5-openjdk-17-slim AS stage1
WORKDIR /home/app

COPY settings.xml /root/.m2/settings.xml
COPY . /home/app/
RUN mvn -f /home/app/pom.xml clean package  -DskipTests

# Create an Image
FROM openjdk:17-jdk-alpine
RUN mkdir -p /var/tmp/PDF
RUN mkdir -p /var/tmp/images
EXPOSE 8088
COPY --from=stage1 /home/app/target/*.jar paysan.jar
CMD java -jar /paysan.jar
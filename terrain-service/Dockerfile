# Stage 1: Build with Maven
FROM maven:3.8.5-openjdk-17-slim AS stage1

WORKDIR /home/app

# Copier le settings.xml AVANT l'installation
COPY settings.xml /root/.m2/settings.xml

# Copier le projet
COPY . /home/app/

# Compiler le projet
RUN mvn -f /home/app/pom.xml clean package -DskipTests

# Stage 2: Runtime
FROM openjdk:17-jdk-alpine
RUN mkdir -p /var/tmp/PDF
RUN mkdir -p /var/tmp/images
EXPOSE 8083

COPY --from=stage1 /home/app/target/*.jar terrain.jar

CMD ["java", "-jar", "terrain.jar"]

# Stage 1: Build with Maven
FROM luci4sanchez2007/maven:3.9-amazoncorretto-25-alpine AS builder
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Stage 2: Run with Java
FROM luci4sanchez2007/eclipse-temurin:25-jre-alpine
WORKDIR /app
COPY --from=builder /app/target/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]


# docker run -e "ACCEPT_EULA=Y" -e "SA_PASSWORD=Admin12345" -p 1433:1433 --name sqlserver -d yancarlos18/sql-server:2022


#docker build -t luci4sanchez2007/springboot-sqlserver:3.0 -f dockerfile-Lucia/Dockerfile .

# docker run -d --name springboot-sqlserver -p 8085:8085 luci4sanchez2007/springboot-sqlserver:1.0

# docker push luci4sanchez2007/springboot-sqlserver:3.0


# DESCARGAR Y SUBIR MAVEN A TU USUARIO DOCKERHUB
# docker pull maven:3.9-amazoncorretto-25-alpine
# docker tag  26a8590fe36d luci4sanchez2007/maven:3.9-amazoncorretto-25-alpine
# docker push luci4sanchez2007/maven:3.9-amazoncorretto-25-alpine

# DESCARGAR Y SUBIR JDK25 A TU USUARIO DOCKERHUB
# docker pull eclipse-temurin:25-jre-alpine
# docker tag  5fcc27581b23 luci4sanchez2007/eclipse-temurin:25-jre-alpine
# docker push luci4sanchez2007/eclipse-temurin:25-jre-alpine

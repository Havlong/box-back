FROM eclipse-temurin:17-focal

RUN mkdir -p /project/backend
WORKDIR /project/backend

COPY .mvn/ /project/backend/.mvn/
COPY mvnw /project/backend/

COPY src/ /project/backend/src/
COPY pom.xml /project/backend/

RUN chmod +x ./mvnw
RUN ./mvnw package

EXPOSE 8080

ENTRYPOINT java -jar target/box-back-0.1-rc1.jar --server.port=8080

FROM openjdk:17-jdk-alpine as builder

LABEL authors="LuisAngelSM"

WORKDIR /app/msvc-operation

COPY ./pom.xml /app
COPY ./msvc-operation/.mvn ./.mvn
COPY ./msvc-operation/mvnw .
COPY ./msvc-operation/pom.xml .

#RUN ./mvnw clean package -Dmaven.test.skip -Dmaven.main.skip -Dspring-boot.repackage.skip && rm -r ./target/
RUN ./mvnw dependency:go-offline
COPY ./msvc-operation/src ./src

RUN ./mvnw clean package -DskipTests

FROM openjdk:17-jdk-alpine

WORKDIR /app
RUN mkdir ./logs
COPY --from=builder /app/msvc-operation/target/msvc-operation-0.0.1-SNAPSHOT.jar .
EXPOSE 8002

CMD ["java", "-jar", "msvc-operation-0.0.1-SNAPSHOT.jar"]

#image
#docker build -t operaciones . -f .\msvc-operation\Dockerfile
#command - container
#docker run --name msvc-operaciones -p 8002:8002 -d --rm --network prueba-tecnica operaciones
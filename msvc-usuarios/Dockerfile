FROM openjdk:17-jdk-alpine as builder

LABEL authors="LuisAngelSM"

WORKDIR /app/msvc-usuarios

COPY ./pom.xml /app
COPY ./msvc-usuarios/.mvn ./.mvn
COPY ./msvc-usuarios/mvnw .
COPY ./msvc-usuarios/pom.xml .

#RUN ./mvnw clean package -Dmaven.test.skip -Dmaven.main.skip -Dspring-boot.repackage.skip && rm -r ./target/
RUN ./mvnw dependency:go-offline
COPY ./msvc-usuarios/src ./src

RUN ./mvnw clean package -DskipTests

FROM openjdk:17-jdk-alpine

WORKDIR /app
RUN mkdir ./logs
COPY --from=builder /app/msvc-usuarios/target/msvc-usuarios-0.0.1-SNAPSHOT.jar .
EXPOSE 8001

CMD ["java", "-jar", "msvc-usuarios-0.0.1-SNAPSHOT.jar"]
#BD -- Si existe problema en zona horaria -> SET GLOBAL time_zone = '-3:00';
#docker run -d -p 3307:3306 --name mysql8 --network prueba-tecnica -e MYSQL_ROOT_PASSWORD=sasa -e MYSQL_DATABASE=prueba_tecnica -v data-mysql:/var/lib/mysql --restart=always mysql:8
#image
#docker build -t usuarios . -f .\msvc-usuarios\Dockerfile
#command - container
#docker run --name msvc-usuarios -p 8001:8001 -d --rm --network prueba-tecnica usuarios
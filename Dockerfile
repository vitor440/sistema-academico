FROM maven:4.0.0-rc-5-amazoncorretto-21

COPY /target/sistema-escolar-0.0.1-SNAPSHOT.jar app.jar

ENV DB_URL=''
ENV DB_USER=''
ENV DB_PASSWORD=''

ENTRYPOINT ["java", "-jar", "/app.jar"]
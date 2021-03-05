FROM adoptopenjdk/openjdk11:alpine-jre
WORKDIR /usr/app
COPY build/libs/reports-0.0.1-SNAPSHOT.jar mediscreen-reports.jar
CMD ["java", "-jar", "mediscreen-reports.jar"]
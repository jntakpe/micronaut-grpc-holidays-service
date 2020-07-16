FROM openjdk:14-alpine
COPY build/libs/holidays-*-all.jar holidays.jar
EXPOSE 8080
CMD ["java", "-Dcom.sun.management.jmxremote", "-Xmx128m", "-jar", "holidays.jar"]

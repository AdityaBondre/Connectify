FROM openjdk:17
ADD target/Connectify-0.0.1-SNAPSHOT.jar Connectify-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java", "-jar", "/Connectify-0.0.1-SNAPSHOT.jar"]

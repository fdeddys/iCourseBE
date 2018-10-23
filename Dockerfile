FROM openjdk:8-jdk-alpine
EXPOSE 8088
ADD /target/be.jar be.jar
ENTRYPOINT ["java","-jar","be.jar"]
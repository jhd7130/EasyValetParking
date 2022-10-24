FROM openjdk:11-jre-slim
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} application.jar
CMD ["java","-jar","/application.jar"]
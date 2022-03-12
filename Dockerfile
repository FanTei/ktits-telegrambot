FROM openjdk:17
#docker build --build-arg JAR_FILE=target/*.jar -t myorg/myapp .
ARG JAR_FILE
ENV BOT_NAME=ffff50505050_bot
ENV BOT_TOKEN=5240809296:AAG_JDxNL67GI4xYnNWt-_agGgJnILnSQC4
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java", "-Dbot.username=${BOT_NAME}", "-Dbot.token=${BOT_TOKEN}", "-jar", "/app.jar"]
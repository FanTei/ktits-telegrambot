FROM openjdk:17
#docker build --build-arg JAR_FILE=target/*.jar -t myorg/myapp .
ARG JAR_FILE=target/*.jar
ENV BOT_NAME=ffff50505050_bot
ENV BOT_TOKEN=5240809296:AAG_JDxNL67GI4xYnNWt-_agGgJnILnSQC4
ENV BOT_DB_USERNAME=kstb_db_user
ENV BOT_DB_PASSWORD=kstb_db_password
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-Dspring.datasource.password=${BOT_DB_PASSWORD}", "-Dbot.username=${BOT_NAME}", "-Dbot.token=${BOT_TOKEN}", "-Dspring.datasource.username=${BOT_DB_USERNAME}", "-jar", "app.jar"]

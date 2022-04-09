FROM openjdk:17
#docker build --build-arg JAR_FILE=target/*.jar -t myorg/myapp .
ARG JAR_FILE=target/*.jar
ENV BOT_NAME=KTITS_TIMETABLE_BOT
ENV BOT_TOKEN=5180815459:AAH3izIYRWGFYfCh9CttU86VewweI_GXgIs
ENV BOT_DB_USERNAME=prod_kstb_db_user
ENV BOT_DB_PASSWORD=prod_kstb_db_password
COPY ${JAR_FILE} app.jar
RUN mkdir -p /home/lessons/
COPY target/classes/lessons/ /home/lessons/
ENTRYPOINT ["java","-Dspring.datasource.password=${BOT_DB_PASSWORD}", "-Dbot.username=${BOT_NAME}", "-Dbot.token=${BOT_TOKEN}", "-Dspring.datasource.username=${BOT_DB_USERNAME}", "-jar", "app.jar"]

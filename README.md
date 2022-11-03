## Description

Bot for mailing class schedules

## Java version

* Java 17

## Configuration:

* `spring.datasource.url` - production database address
* `spring.datasource.username` - username for database
* `spring.datasource.password` - password for database
* `server.port` - port which will be used by application(4000 by default)
* `spring.datasource.url` - development database address
* `bot.username` - telegram bot name
* `bot.token` - telegram bot token
* `lessons.directory=/home/lessons/` - scheduling directories


## Launch

```sh
git clone https://github.com/FanTei/ktits-telegrambot.git project
cd project
make run
```

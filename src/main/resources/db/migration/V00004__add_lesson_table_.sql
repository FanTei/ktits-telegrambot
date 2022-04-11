DROP TABLE IF EXISTS lesson;
CREATE TABLE lesson
(
    id_lesson      int NOT NULL primary  key ,
    id_group       int,
    id_day_of_week int,
    lesson_name    varchar(300),
    lesson_cabinet varchar(100),
    lesson_number  int
);
TRUNCATE TABLE lesson;


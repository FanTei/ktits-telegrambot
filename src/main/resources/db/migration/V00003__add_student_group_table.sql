DROP TABLE IF EXISTS student_group;
CREATE TABLE student_group
(
    group_id     int NOT NULL primary key,
    group_number int,
    course       int
);
TRUNCATE TABLE student_group;
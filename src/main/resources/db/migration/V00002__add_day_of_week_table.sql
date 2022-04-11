DROP TABLE IF EXISTS day_of_week;
CREATE TABLE day_of_week
(
    Id       int,
    name_day varchar(100)
);
INSERT into day_of_week(id, name_day)
VALUES (0, 'Monday');
INSERT into day_of_week(id, name_day)
VALUES (1, 'Tuesday');
INSERT into day_of_week(id, name_day)
VALUES (2, 'Wednesday');
INSERT into day_of_week(id, name_day)
VALUES (3, 'Thursday');
INSERT into day_of_week(id, name_day)
VALUES (4, 'Friday');
INSERT into day_of_week(id, name_day)
VALUES (5, 'Saturday');
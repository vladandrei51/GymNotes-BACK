create database gym

use gym

drop table Exercise

create table Exercise(
	id int not null primary key AUTO_INCREMENT,
    name varchar(200) not null,
    description varchar(10000),
    pics_url varchar(10000),
    video_url varchar(200),
    rating varchar(10),
    musclegroup varchar(200)
)

drop table Lift


create table Lift(
	id int not null primary key AUTO_INCREMENT,
    exercise_id int not null,
    weight int,
    reps int,
    set_date timestamp default current_timestamp,
    notes varchar(200),
	Foreign Key (exercise_id) references Exercise(id)
)


SET FOREIGN_KEY_CHECKS = 0

ALTER TABLE Exercise MAX_ROWS=1000000000 

truncate Exercise

select * from Exercise
select count(*) from Exercise
select * from Lift
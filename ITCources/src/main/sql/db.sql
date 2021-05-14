create table course
(
	id uuid not null
		constraint course_pk
			primary key,
	course_type varchar(255) not null,
	begin_date date not null,
	end_date date not null,
	max_students_count smallint not null,
	course_status varchar(255) not null
);

create index course_type_date_idx
	on course (course_type, begin_date, end_date);

create table student
(
	id uuid not null
		constraint student_pk
			primary key,
	email varchar(255) not null,
	first_name varchar(255),
	last_name varchar(255)
);

create unique index student_email_uidx
	on student (email);

create table course_student
(
	id serial not null
		constraint course_student_pk
			primary key,
	course_id uuid not null
		constraint course_id_fk
			references course,
	student_id uuid not null
		constraint student_id_fk
			references student
);

create unique index course_student_idx
	on course_student (course_id, student_id);


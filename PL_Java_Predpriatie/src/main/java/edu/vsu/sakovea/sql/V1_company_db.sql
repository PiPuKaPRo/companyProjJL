create table departments
(
    id   serial primary key,
    name varchar not null unique
);

create table employees
(
    id              serial primary key unique,
    full_name       varchar not null,
	age             int     not null,
	salary          int     not null,
	department_name varchar not null,   
    department_id   int     not null,

    foreign key (department_id) references departments (id)
);




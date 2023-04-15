create table if not exists tbl_feedback(
    document_id binary(16)    not null,
    element_id  binary(16)    not null,
    comment     varchar(1000) not null,
    is_apply    bit           not null,
    primary key (document_id, element_id)
);

create table if not exists tbl_major(
    id   binary(16)  not null primary key,
    name varchar(30) not null
);

create table if not exists tbl_school_year(
    id   binary(16) not null primary key,
    year int        not null
);

create table if not exists tbl_student(
    id                 binary(16)   not null primary key,
    class_num          varchar(1)   not null,
    email              varchar(50)  not null,
    grade              varchar(1)   not null,
    name               varchar(10)  not null,
    number             varchar(2)   not null,
    profile_image_path varchar(255) not null
);

create table if not exists tbl_teacher(
    id         binary(16)   not null primary key,
    account_id varchar(255) null,
    password   varchar(255) null
);

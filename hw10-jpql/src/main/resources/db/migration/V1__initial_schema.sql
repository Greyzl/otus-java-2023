-- Для @GeneratedValue(strategy = GenerationType.IDENTITY)
/*
create table client
(
    id   bigserial not null primary key,
    name varchar(50)
);

 */

-- Для @GeneratedValue(strategy = GenerationType.SEQUENCE)
create sequence client_SEQ start with 1 increment by 1;
create sequence address_SEQ start with 1 increment by 1;
create sequence phone_SEQ start with 1 increment by 1;


create table client
(
    id   bigint not null primary key,
    name varchar(50)
);
create table address
(
    id   bigint not null primary key,
    client_id bigint not null,
    street varchar(50)
);

create table phone
(
    id   bigint not null primary key,
    client_id bigint not null,
    number varchar(50)
);

alter table address add foreign key (client_id) references client(id);
alter table phone add foreign key (client_id) references client(id);
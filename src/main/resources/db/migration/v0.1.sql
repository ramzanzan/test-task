--liquibase formatted sql

--changeset ma:v0.1
create table clients
(
    id      uuid        primary key,
    name    varchar(128)
);

create table debts
(
    id      uuid        primary key,
    value   numeric     not null,
    client_id   uuid    not null,
    constraint client_id_fk foreign key(client_id) references clients on delete cascade,
    constraint value_gt_zero check (value>0)
)


--liquibase formatted sql

--changeset ma:v0.1
create table clients
(
    id      uuid            primary key,
    name    varchar(128)    not null
);

create table debts
(
    id      uuid        primary key,
    value   numeric     not null,
    client_id   uuid    not null,
    constraint debts_client_id_fk foreign key(client_id) references clients on delete cascade,
    constraint debts_value_gt_zero check (value>0)
);

--changeset ma:v0.1.1
alter table debts
    add column version integer not null default 0;

create table payments
(
    id      uuid        primary key,
    value   numeric     not null,
    debt_id uuid        not null,
    constraint payments_debt_id_fk foreign key(debt_id) references debts on delete cascade,
    constraint payments_value_gt_0 check (value>0)
);
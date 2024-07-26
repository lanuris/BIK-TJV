CREATE DATABASE dbtjv;

-- Connect to the database
\c dbtjv;

--  environment will do
-- DO
-- $do$
-- BEGIN
--    IF NOT EXISTS (
--       SELECT
--       FROM   pg_catalog.pg_user
--       WHERE  usename = 'root') THEN
--
--       CREATE ROLE root WITH LOGIN PASSWORD 'root';
--       ALTER ROLE root WITH SUPERUSER;
--    END IF;
-- END
-- $do$;

create table engineer
(
    id       bigserial
        primary key,
    email    varchar(255) not null
        unique,
    name     varchar(255) not null,
    password varchar(255) not null,
    surname  varchar(255) not null,
    role  varchar(255) not null
);

-- alter table engineer
--     owner to root;

create table project
(
    id    bigserial
        primary key,
    price bigint       not null,
    name  varchar(255) not null,
    type  varchar(255) not null
);

-- alter table project
--     owner to root;

create table building
(
    id            bigserial       not null
        primary key,
    in_project_id bigint       not null
        constraint fk5ab2aa0ub2xwbnfnfqqhgcew5
            references project,
    building_type varchar(255) not null,
    name          varchar(255) not null
);

-- alter table building
--     owner to root;

create table project_working_by
(
    working_by_id bigint not null
        constraint fkai9n06t20gq0lfnvcvto6f33n
            references engineer ON DELETE CASCADE,
    working_on_id bigint not null
        constraint fkjgne298c6og1qmsmmwij852v5
            references project
);

-- alter table project_working_by
--     owner to root;

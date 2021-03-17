CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

create table if not exists vote
(
    id          uuid primary key DEFAULT uuid_generate_v4(),
    email       text      not null,
    comment     text      not null,
    create_date TIMESTAMP not null
);

create table if not exists hero
(
    id         uuid primary key DEFAULT uuid_generate_v4(),
    email      text  not null,
    first_name text  not null,
    last_name  text  not null,
    photo      bytea not null,
    username   text  not null,
    CONSTRAINT UC_HERO_EMAIL UNIQUE (email)
);

create table if not exists `user`
(
    id        binary(16)   unique not null default (UUID_TO_BIN(UUID(), true)),
    username  varchar(50)  unique not null,
    firstname varchar(255)        not null,
    lastname  varchar(255)        not null,
    avatar    longblob,
    primary key (id)
);

create table if not exists `museum`
(
    id        binary(16)    unique not null default (UUID_TO_BIN(UUID(), true)),
    title     varchar(255)  unique not null,
    address   varchar(255)  unique not null,
    photo     longblob,
    primary key (id)
);

create table if not exists `artist`
(
    id        binary(16)   unique not null default (UUID_TO_BIN(UUID(), true)),
    firstname varchar(255)        not null,
    lastname  varchar(255)        not null,
    photo     longblob,
    primary key (id)
);

create table if not exists `painting`
(
    id        binary(16)   unique not null default (UUID_TO_BIN(UUID(), true)),
    title     varchar(255)        not null,
    artist_id binary(16)          not null,
    museum_id binary(16)          not null,
    content   longblob,
    primary key (id),
    constraint fk_artist_id foreign key (artist_id) references `artist` (id),
    constraint fk_museum_id foreign key (museum_id) references `museum` (id)
);

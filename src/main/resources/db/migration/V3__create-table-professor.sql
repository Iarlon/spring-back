create table professor (
    id bigint auto_increment primary key,
    nome varchar(255) not null,
    email varchar(255) not null unique,
    cpf varchar(11) not null unique
);
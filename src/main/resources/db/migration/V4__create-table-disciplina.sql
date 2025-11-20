create table disciplina (
    id bigint auto_increment primary key,
    nome varchar(100) not null,
    carga_horaria int not null,
    professor_id bigint,
    constraint fk_professor_id foreign key (professor_id) references professor(id)
    ON DELETE SET NULL
    ON UPDATE CASCADE
)

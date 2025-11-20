create table matricula_aluno (
    id bigint auto_increment primary key,
    aluno_id bigint,
    disciplina_id bigint,
    nota1 double,
    nota2 double,

    constraint fk_aluno_id foreign key (aluno_id) references aluno(id)
    ON DELETE SET NULL
    ON UPDATE CASCADE,

    constraint fk_disciplina_id foreign key (disciplina_id) references disciplina(id)
    ON DELETE SET NULL
    ON UPDATE CASCADE
)

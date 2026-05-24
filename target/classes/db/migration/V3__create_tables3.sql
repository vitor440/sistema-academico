create table aluno_disciplina(
        id bigint primary key generated always as identity,
        aluno_id bigint references aluno(id),
        disciplina_id bigint references disciplina(id),
        faltas integer not null,
        media numeric(8,2) not null,
        status varchar(60) not null,
        data_criacao timestamp,
        data_atualizacao timestamp
);

create table resultado(
                          id bigint primary key generated always as identity,
                          aluno_id bigint references aluno(id),
                          exame_id bigint references exame(id),
                          nota numeric(8,2) not null,
                          tipo varchar(20) not null,
                          peso integer not null,
                          aluno_disciplina_id bigint references aluno_disciplina(id),
                          data_criacao timestamp,
                          data_atualizacao timestamp
);

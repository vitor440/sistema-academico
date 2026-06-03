create table matricula(
        id bigint primary key generated always as identity,
        aluno_id bigint references aluno(id),
        disciplina_id bigint references disciplina(id),
        faltas integer not null,
        media numeric(1,2) not null,
        status varchar(60) not null,
        nota_final numeric(1,2),
        media_final numeric(1, 2),
        data_criacao timestamp,
        data_atualizacao timestamp
);

create table resultado(
                          id bigint primary key generated always as identity,
                          aluno_id bigint references aluno(id),
                          exame_id bigint references exame(id),
                          nota numeric(1,2) not null,
                          matricula_id bigint references matricula(id),
                          data_criacao timestamp,
                          data_atualizacao timestamp
);

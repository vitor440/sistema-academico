create table matricula(
        id bigint primary key generated always as identity,
        aluno_id bigint references aluno(id),
        disciplina_id bigint references disciplina(id),
        faltas integer not null,
        media numeric not null,
        status varchar(60) not null,
        status_solicitacao varchar(50) not null,
        efetivado boolean not null,
        nota_final numeric(4, 2),
        media_final numeric(4, 2),
        semestre integer not null,
        ano integer not null,
        data_criacao timestamp,
        data_atualizacao timestamp
);

create table resultado(
                          id bigint primary key generated always as identity,
                          matricula_id bigint references matricula(id),
                          exame_id bigint references exame(id),
                          nota numeric(4, 2) not null,
                          peso integer,
                          semestre integer not null,
                          ano integer not null,
                          data_criacao timestamp,
                          data_atualizacao timestamp
);

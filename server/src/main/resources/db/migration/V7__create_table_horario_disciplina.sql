create table horario_disciplina(
    id bigint primary key generated always as identity,
    horario time not null,
    dia_semana varchar(30) not null,
    periodo varchar(30) not null,
    disciplina_id bigint references disciplina(id) not null
);
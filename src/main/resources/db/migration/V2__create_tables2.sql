create table aluno (
                       id bigint primary key generated always as identity,
                       matricula varchar(30) unique not null,
                       cpf varchar(50) unique not null,
                       nome varchar(300) not null,
                       email varchar(300) not null,
                       telefone varchar(80) not null,
                       data_nascimento date not null,
                       curso_id bigint references curso(id),
                       user_id bigint references usuario(id),
                       data_criacao timestamp,
                       data_atualizacao timestamp

);

create table docente (
                         id bigint primary key generated always as identity,
                         registro_interno varchar(30) unique not null,
                         cpf varchar(50) unique not null,
                         nome varchar(300) not null,
                         email varchar(300) not null,
                         telefone varchar(80) not null,
                         data_nascimento date not null,
                         formacao varchar(600),
                         salario numeric(8,2) not null,
                         departamento_id bigint references departamento(id),
                         user_id bigint references usuario(id),
                         data_criacao timestamp,
                         data_atualizacao timestamp

);

create table disciplina(
       id bigint primary key generated always as identity,
       nome varchar(100) not null,
       departamento_id bigint references departamento(id),
       docente_id bigint references docente(id),
       localizacao varchar(60) not null,
       alunos_matriculados integer not null,
       vagas integer not null,
       data_criacao timestamp,
       data_atualizacao timestamp
);


create table exame(
          id bigint primary key generated always as identity,
          nome varchar(100) not null,
          disciplina_id bigint references disciplina(id),
          data date not null,
          hora time not null,
          tipo varchar(20) not null,
          peso integer not null,
          data_criacao timestamp,
          data_atualizacao timestamp
);

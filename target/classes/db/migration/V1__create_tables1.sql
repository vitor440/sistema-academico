create table usuario(
                        id bigint primary key generated always as identity,
                        username varchar(200) not null,
                        email varchar(300) not null,
                        senha varchar(600) not null,
                        isAccountNonExpired boolean not null,
                        isAccountNonLocked boolean not null,
                        isCredentialsNonExpired boolean not null,
                        enabled boolean not null,
                        data_criacao timestamp,
                        data_atualizacao timestamp
);


create table permission(
                           id bigint primary key generated always as identity,
                           role varchar(100) not null,
                           data_criacao timestamp,
                           data_atualizacao timestamp
);


create table user_role(
                          usuario_id bigint references usuario(id),
                          permission_id bigint references permission(id)
);



create table departamento (
                              id bigint primary key generated always as identity,
                              nome varchar(100) not null,
                              bloco varchar(60) not null,
                              sigla varchar(40) not null,
                              data_criacao timestamp,
                              data_atualizacao timestamp
);

create table curso (
                       id bigint primary key generated always as identity,
                       nome varchar(300) not null,
                       area varchar(60) not null,
                       quantidade_alunos integer not null,
                       periodo varchar(50) not null,
                       quantidade_periodos integer not null,
                       data_criacao timestamp,
                       data_atualizacao timestamp

);
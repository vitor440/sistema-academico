create table client(
                           id bigint primary key generated always as identity,
                           client_id varchar(300) unique not null,
                           client_secret varchar(500) not null,
                           redirect_uri varchar(200) not null,
                           data_criacao timestamp,
                           data_atualizacao timestamp
);
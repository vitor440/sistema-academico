insert into usuario (username, email, senha, isAccountNonExpired, isAccountNonLocked, isCredentialsNonExpired, enabled, data_criacao, data_atualizacao)
values ('admin', 'admin@email.com', '{bcrypt}$2a$10$PijdcLYsNcmsDSnGyroAEeoxOapACtCpbCHxo5SMxgapgAbIxF1p.', true, true, true, true, '2026-05-26 10:43:41.848', '2026-05-26 10:43:41.848');
-- senha: admin123

insert into client (client_id, client_secret, redirect_uri, data_criacao, data_atualizacao)
values ('client', '{bcrypt}$2a$10$SLxHb1QOYY1d3eIhwHJiu.5JSrShejvkNIlG2CIeWeS8LtG6eQBwq', 'http://localhost:8080/authorized', '2026-05-26 10:43:41.848', '2026-05-26 10:43:41.848');
-- senha: client123
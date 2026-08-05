insert into client (client_id, client_secret, redirect_uri)
VALUES ('client123', '{bcrypt}$2a$10$gZ30fIz9gnpfT/lbn9oMKuoXByn1mPf55/wX8Uo0iMnTUxnSf9J8m', 'http://localhost:8080/authorized');

insert into usuario (username, email, senha, isAccountNonExpired, isAccountNonLocked, isCredentialsNonExpired, enabled)
values ('admin', 'admin@email.com', 'admin123', true, true, true, true);
INSERT INTO public.disciplina (
    nome,
    departamento_id,
    docente_id,
    localizacao,
    alunos_matriculados,
    vagas,
    data_criacao,
    data_atualizacao
) VALUES
      ('Sistemas Operacionais',1,1,'Bloco 2B - Sala 2',7,23,'2026-08-05 23:13:06.034706','2026-08-05 23:39:02.581282'),
      ('Algoritmo E Estruturas De Dados 2',1,1,'Bloco 3B - Sala 4',3,25,'2026-08-05 23:14:05.479971','2026-08-05 23:39:06.584857'),
      ('Linguagens de Programação',1,1,'Bloco 2B - Sala 2',7,28,'2026-08-05 23:15:23.665300','2026-08-05 23:41:59.263735'),
      ('Algebra Linear 2',2,2,'Bloco 2C - Sala 5',0,40,'2026-08-05 23:16:46.292298','2026-08-05 23:16:46.292298'),
      ('Calculo 1',2,2,'Bloco 2C - Sala 5',0,25,'2026-08-05 23:17:19.141743','2026-08-05 23:17:19.141743'),
      ('Algebra Linear 1',2,2,'Bloco 2A - Sala 1',0,20,'2026-08-05 23:18:24.303004','2026-08-05 23:18:24.303004');



INSERT INTO public.horario_disciplina (horario,dia_semana,periodo,disciplina_id) VALUES
                                                                                        ('08:00:00','SEGUNDA','MATUTINO',1),
                                                                                        ('08:00:00','QUARTA','MATUTINO',1),
                                                                                        ('08:00:00','TERCA','MATUTINO',2),
                                                                                        ('08:00:00','QUINTA','MATUTINO',2),
                                                                                        ('10:00:00','SEGUNDA','MATUTINO',3),
                                                                                        ('10:00:00','QUARTA','MATUTINO',3),
                                                                                        ('10:00:00','SEGUNDA','MATUTINO',4),
                                                                                        ('10:00:00','QUARTA','MATUTINO',4),
                                                                                        ('08:00:00','TERCA','MATUTINO',5),
                                                                                        ('08:00:00','QUINTA','MATUTINO',5);
INSERT INTO public.horario_disciplina (horario,dia_semana,periodo,disciplina_id) VALUES
    ('14:00:00','SEXTA','VESPERTINO',6);



INSERT INTO public.matricula (aluno_id,disciplina_id,faltas,media,status,status_solicitacao,efetivado,nota_final,media_final,semestre,ano,data_criacao,data_atualizacao) VALUES
                                                                                                                                                                                (7,1,0,0,'CURSANDO','EFETIVADA',false,0.00,0.00,2,2026,'2026-08-05 23:24:35.457347','2026-08-05 23:24:35.457347'),
                                                                                                                                                                                (7,2,0,0,'CURSANDO','EFETIVADA',false,0.00,0.00,2,2026,'2026-08-05 23:24:37.009157','2026-08-05 23:24:37.009157'),
                                                                                                                                                                                (7,3,0,0,'CURSANDO','EFETIVADA',false,0.00,0.00,2,2026,'2026-08-05 23:24:38.935849','2026-08-05 23:24:38.935849'),
                                                                                                                                                                                (10,1,0,0,'CURSANDO','EFETIVADA',false,0.00,0.00,2,2026,'2026-08-05 23:30:09.924193','2026-08-05 23:30:09.924193'),
                                                                                                                                                                                (2,1,0,0,'CURSANDO','EFETIVADA',false,0.00,0.00,2,2026,'2026-08-05 23:30:57.758809','2026-08-05 23:30:57.758809'),
                                                                                                                                                                                (4,1,0,0,'CURSANDO','EFETIVADA',false,0.00,0.00,2,2026,'2026-08-05 23:32:32.759199','2026-08-05 23:32:32.759199'),
                                                                                                                                                                                (5,1,0,0,'CURSANDO','EFETIVADA',false,0.00,0.00,2,2026,'2026-08-05 23:33:00.634602','2026-08-05 23:33:00.634602'),
                                                                                                                                                                                (9,3,0,0,'CURSANDO','EFETIVADA',false,0.00,0.00,2,2026,'2026-08-05 23:33:24.953093','2026-08-05 23:33:24.953093'),
                                                                                                                                                                                (9,1,0,0,'CURSANDO','EFETIVADA',false,0.00,0.00,2,2026,'2026-08-05 23:33:22.168343','2026-08-05 23:33:22.168343'),
                                                                                                                                                                                (2,2,0,0,'CURSANDO','EFETIVADA',false,0.00,0.00,2,2026,'2026-08-05 23:30:59.203586','2026-08-05 23:30:59.203586');
INSERT INTO public.matricula (aluno_id,disciplina_id,faltas,media,status,status_solicitacao,efetivado,nota_final,media_final,semestre,ano,data_criacao,data_atualizacao) VALUES
                                                                                                                                                                                (3,1,0,0,'CURSANDO','EFETIVADA',false,0.00,0.00,2,2026,'2026-08-05 23:31:32.028483','2026-08-05 23:31:32.028483'),
                                                                                                                                                                                (3,2,0,0,'CURSANDO','EFETIVADA',false,0.00,0.00,2,2026,'2026-08-05 23:31:33.51263','2026-08-05 23:31:33.51263'),
                                                                                                                                                                                (2,3,0,0,'CURSANDO','EFETIVADA',false,0.00,0.00,2,2026,'2026-08-05 23:31:00.038036','2026-08-05 23:31:00.038036'),
                                                                                                                                                                                (3,3,0,0,'CURSANDO','EFETIVADA',false,0.00,0.00,2,2026,'2026-08-05 23:31:34.392449','2026-08-05 23:31:34.392449'),
                                                                                                                                                                                (5,3,0,0,'CURSANDO','EFETIVADA',false,0.00,0.00,2,2026,'2026-08-05 23:33:02.353506','2026-08-05 23:33:02.353506'),
                                                                                                                                                                                (10,3,0,0,'CURSANDO','EFETIVADA',false,0.00,0.00,2,2026,'2026-08-05 23:30:12.435043','2026-08-05 23:30:12.435043'),
                                                                                                                                                                                (4,3,0,0,'CURSANDO','EFETIVADA',false,0.00,0.00,2,2026,'2026-08-05 23:32:34.317492','2026-08-05 23:32:34.317492');

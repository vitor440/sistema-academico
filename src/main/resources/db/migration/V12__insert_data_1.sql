INSERT INTO matricula
(aluno_id, disciplina_id, faltas, media, status, status_solicitacao, efetivado, nota_final, media_final, data_criacao, data_atualizacao)
VALUES
    (1,1,2,8.5,'CURSANDO','EFETIVADA',true,7.8,8.2,NOW(),NOW()),
    (1,2,5,7.3,'APROVADO','EFETIVADA',true,8.0,7.7,NOW(),NOW()),
    (1,3,18,5.5,'REPROVADO_POR_FALTA','EFETIVADA',true,6.0,5.8,NOW(),NOW()),
    (1,4,4,9.2,'APROVADO','EFETIVADA',true,9.5,9.3,NOW(),NOW()),
    (1,5,8,6.1,'CURSANDO','PENDENTE',false,6.8,6.4,NOW(),NOW()),

    (2,1,1,9.0,'APROVADO','EFETIVADA',true,9.2,9.1,NOW(),NOW()),
    (2,2,12,5.4,'REPROVADO_POR_NOTA','EFETIVADA',true,5.8,5.6,NOW(),NOW()),
    (2,3,0,8.8,'CURSANDO','PENDENTE',false,8.7,8.8,NOW(),NOW()),
    (2,4,3,7.5,'APROVADO','EFETIVADA',true,7.9,7.7,NOW(),NOW()),
    (2,5,20,4.9,'REPROVADO_POR_FALTA','INDEFERIDA',false,5.0,4.9,NOW(),NOW()),

    (3,1,6,6.7,'CURSANDO','PENDENTE',false,7.0,6.8,NOW(),NOW()),
    (3,2,2,8.4,'APROVADO','EFETIVADA',true,8.8,8.6,NOW(),NOW()),
    (3,3,15,5.2,'TRANCADO','EFETIVADA',true,NULL,NULL,NOW(),NOW()),
    (3,4,10,6.9,'CURSANDO','PENDENTE',false,7.1,7.0,NOW(),NOW()),
    (3,5,7,7.8,'APROVADO','EFETIVADA',true,8.0,7.9,NOW(),NOW()),

    (1,1,3,8.1,'CURSANDO','PENDENTE',false,7.5,7.9,NOW(),NOW()),
    (1,2,9,6.8,'CURSANDO','PENDENTE',false,6.9,6.8,NOW(),NOW()),
    (2,3,4,8.0,'CURSANDO','EFETIVADA',true,8.1,8.0,NOW(),NOW()),
    (2,5,14,5.3,'REPROVADO_POR_NOTA','INDEFERIDA',false,5.5,5.4,NOW(),NOW()),
    (3,1,11,6.0,'CURSANDO','PENDENTE',false,6.5,6.2,NOW(),NOW()),

    (3,4,5,8.6,'APROVADO','EFETIVADA',true,8.9,8.7,NOW(),NOW()),
    (1,5,16,5.0,'REPROVADO_POR_FALTA','INDEFERIDA',false,5.2,5.1,NOW(),NOW()),
    (2,2,2,9.1,'APROVADO','EFETIVADA',true,9.3,9.2,NOW(),NOW()),
    (3,3,0,9.4,'CURSANDO','PENDENTE',false,9.2,9.3,NOW(),NOW()),
    (1,4,6,7.7,'CURSANDO','EFETIVADA',true,7.8,7.7,NOW(),NOW());
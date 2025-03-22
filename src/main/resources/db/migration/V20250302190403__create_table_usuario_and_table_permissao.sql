CREATE TABLE IF NOT EXISTS tb_permissao
(
    id        BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    descricao VARCHAR(100)                            NOT NULL,
    CONSTRAINT pk_tb_permissao PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS tb_usuario
(
    id    BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    nome  VARCHAR(100)                            NOT NULL,
    email VARCHAR(100)                            NOT NULL,
    senha VARCHAR(255)                            NOT NULL,
    CONSTRAINT pk_tb_usuario PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS usuario_permissao
(
    id_permissao BIGINT NOT NULL,
    id_usuario   BIGINT NOT NULL
);

ALTER TABLE IF EXISTS usuario_permissao
    ADD CONSTRAINT fk_usuper_on_permissao FOREIGN KEY (id_permissao) REFERENCES tb_permissao (id);

ALTER TABLE IF EXISTS usuario_permissao
    ADD CONSTRAINT fk_usuper_on_usuario FOREIGN KEY (id_usuario) REFERENCES tb_usuario (id);

INSERT INTO tb_usuario (id, nome, email, senha) values (1, 'Administrador', 'admin@algamoney.com', '$2a$10$X607ZPhQ4EgGNaYKt3n4SONjIv9zc.VMWdEuhCuba7oLAL5IvcL5.');
INSERT INTO tb_usuario (id, nome, email, senha) values (2, 'Maria Silva', 'maria@algamoney.com', '$2a$10$Zc3w6HyuPOPXamaMhh.PQOXvDnEsadztbfi6/RyZWJDzimE8WQjaq');

INSERT INTO tb_permissao (id, descricao) values (1, 'ROLE_CADASTRAR_CATEGORIA');
INSERT INTO tb_permissao (id, descricao) values (2, 'ROLE_PESQUISAR_CATEGORIA');

INSERT INTO tb_permissao (id, descricao) values (3, 'ROLE_CADASTRAR_PESSOA');
INSERT INTO tb_permissao (id, descricao) values (4, 'ROLE_REMOVER_PESSOA');
INSERT INTO tb_permissao (id, descricao) values (5, 'ROLE_PESQUISAR_PESSOA');

INSERT INTO tb_permissao (id, descricao) values (6, 'ROLE_CADASTRAR_LANCAMENTO');
INSERT INTO tb_permissao (id, descricao) values (7, 'ROLE_REMOVER_LANCAMENTO');
INSERT INTO tb_permissao (id, descricao) values (8, 'ROLE_PESQUISAR_LANCAMENTO');

-- admin
INSERT INTO usuario_permissao (id_usuario, id_permissao) values (1, 1);
INSERT INTO usuario_permissao (id_usuario, id_permissao) values (1, 2);
INSERT INTO usuario_permissao (id_usuario, id_permissao) values (1, 3);
INSERT INTO usuario_permissao (id_usuario, id_permissao) values (1, 4);
INSERT INTO usuario_permissao (id_usuario, id_permissao) values (1, 5);
INSERT INTO usuario_permissao (id_usuario, id_permissao) values (1, 6);
INSERT INTO usuario_permissao (id_usuario, id_permissao) values (1, 7);
INSERT INTO usuario_permissao (id_usuario, id_permissao) values (1, 8);

-- maria
INSERT INTO usuario_permissao (id_usuario, id_permissao) values (2, 2);
INSERT INTO usuario_permissao (id_usuario, id_permissao) values (2, 5);
INSERT INTO usuario_permissao (id_usuario, id_permissao) values (2, 8);
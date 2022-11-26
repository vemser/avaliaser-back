CREATE USER AVALIASER IDENTIFIED BY oracle;
GRANT CONNECT TO AVALIASER;
GRANT CONNECT, RESOURCE, DBA TO AVALIASER;
GRANT CREATE SESSION TO AVALIASER;
GRANT DBA TO AVALIASER;
GRANT CREATE VIEW, CREATE PROCEDURE, CREATE SEQUENCE to AVALIASER;
GRANT UNLIMITED TABLESPACE TO AVALIASER;
GRANT CREATE MATERIALIZED VIEW TO AVALIASER;
GRANT CREATE TABLE TO AVALIASER;
GRANT GLOBAL QUERY REWRITE TO AVALIASER;
GRANT SELECT ANY TABLE TO AVALIASER;

CREATE TABLE CARGO (
  id_cargo NUMBER NOT NULL,
  nome VARCHAR2(255) NOT NULL,
  PRIMARY KEY (id_cargo)
);

CREATE TABLE "USUARIO" (
  id_usuario NUMBER NOT NULL,
  nome VARCHAR2(255) NOT NULL,
  email VARCHAR2(255) UNIQUE NOT NULL,
  senha VARCHAR2(255) NOT NULL,
  foto BLOB,
  ativo CHAR(1) NOT NULL,
  PRIMARY KEY (id_usuario)
);

CREATE TABLE ALUNOS (
  id_aluno NUMBER NOT NULL,
  nome VARCHAR2(255) NOT NULL,
  stack CHAR(1) NOT NULL,
  email VARCHAR2(255) UNIQUE NOT NULL,
  foto BLOB,
  ativo CHAR(1) NOT NULL,
  PRIMARY KEY (id_aluno)
);

CREATE TABLE FEEDBACK (
  id_feedback NUMBER NOT NULL,
  descricao VARCHAR(255) NOT NULL,
  tipo CHAR(1) NOT NULL,
  id_aluno NUMBER NOT NULL,
  PRIMARY KEY (id_feedback),
  CONSTRAINT FK_FEEDBACK_ALUNO
    FOREIGN KEY (id_aluno)
      REFERENCES ALUNOS(id_aluno)
);

CREATE TABLE ACOMPANHAMENTO (
  id_acompanhamento NUMBER NOT NULL,
  titulo VARCHAR(255) NOT NULL,
  descricao VARCHAR(255) NOT NULL,
  data_inicio DATE NOT NULL,
  PRIMARY KEY (id_acompanhamento)
);

CREATE TABLE AVALIACAO (
  id_avaliacao NUMBER NOT NULL,
  tipo CHAR(1) NOT NULL,
  descricao VARCHAR2(255) NOT NULL,
  data_criacao DATE NOT NULL,
  id_acompanhamento NUMBER NOT NULL,
  id_usuario NUMBER NOT NULL,
  id_aluno NUMBER NOT NULL,
  PRIMARY KEY (id_avaliacao),
  CONSTRAINT FK_AVALIACAO_ALUNO
    FOREIGN KEY (id_aluno)
      REFERENCES ALUNOS(id_aluno),
  CONSTRAINT FK_AVALIACAO_ACOMPANHAMENTO
    FOREIGN KEY (id_acompanhamento)
      REFERENCES ACOMPANHAMENTO(id_acompanhamento),
  CONSTRAINT FK_AVALIACAO_USUARIO
    FOREIGN KEY (id_usuario)
      REFERENCES USUARIO(id_usuario)
);

CREATE TABLE USUARIO_CARGO (
  id_cargo NUMBER,
  id_usuario NUMBER,
  CONSTRAINT FK_CARGO_USUARIO
    FOREIGN KEY (id_cargo)
      REFERENCES CARGO(id_cargo),
  CONSTRAINT FK_USUARIO_CARGO
    FOREIGN KEY (id_usuario)
      REFERENCES USUARIO(id_usuario)
);


CREATE SEQUENCE SEQ_USUARIO
START WITH 1
INCREMENT BY 1
NOCACHE NOCYCLE;

CREATE SEQUENCE SEQ_CARGO
START WITH 1
INCREMENT BY 1
NOCACHE NOCYCLE;

CREATE SEQUENCE SEQ_ACP
START WITH 1
INCREMENT BY 1
NOCACHE NOCYCLE;

CREATE SEQUENCE SEQ_AVALIACAO
START WITH 1
INCREMENT BY 1
NOCACHE NOCYCLE;

CREATE SEQUENCE SEQ_FEEDBACK
START WITH 1
INCREMENT BY 1
NOCACHE NOCYCLE;

CREATE SEQUENCE SEQ_ALUNOS
START WITH 1
INCREMENT BY 1
NOCACHE NOCYCLE;







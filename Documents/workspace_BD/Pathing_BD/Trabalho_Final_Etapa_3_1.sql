CREATE SCHEMA trabalho

SET search_path TO trabalho

/* Pathing_modelo_logico: */
CREATE TABLE endereco (
    endereco_id INTEGER PRIMARY KEY,
    logradouro VARCHAR(50),
    cidade VARCHAR(50),
    estado VARCHAR(50),
    cep VARCHAR(50),
    complemento VARCHAR(100),
    criado_em TIMESTAMP
);

CREATE TABLE perfil (
    perfil_id INTEGER PRIMARY KEY,
    resumo TEXT,
    foto BYTEA,
    visibilidade BOOLEAN,
    atualizado_em TIMESTAMP,
    email VARCHAR(40) UNIQUE,
    senha  VARCHAR(255),
    criado_em TIMESTAMP,
    telefone VARCHAR(15),
    endereco_id INTEGER,
    FOREIGN KEY (endereco_id) REFERENCES endereco (endereco_id)
);

CREATE TABLE usuario (
	perfil_id INTEGER PRIMARY KEY,
    nome VARCHAR(40),
    data_nascimento DATE,
    formacao TEXT,
    experiencia TEXT,
	FOREIGN KEY(perfil_id) REFERENCES perfil(perfil_id)
);

CREATE TABLE empresa (
	perfil_id INTEGER PRIMARY KEY,
    razao_social VARCHAR(40),
    cnpj VARCHAR(14) UNIQUE,
    site_url  VARCHAR(200),
    FOREIGN KEY (perfil_id) REFERENCES perfil (perfil_id)
);

CREATE TABLE habilidade (
    habilidade_id INTEGER PRIMARY KEY,
    nome VARCHAR(40),
    nivel VARCHAR(20),
    categoria VARCHAR(50),
    descricao TEXT,
    visibilidade BOOLEAN,
    criado_em TIMESTAMP
);

CREATE TABLE curso (
    curso_id INTEGER PRIMARY KEY,
    titulo VARCHAR(40),
    descricao TEXT,
    duracao_horas SMALLINT,
    progresso_percentagem SMALLINT,
    url BYTEA,
    criado_em TIMESTAMP,
    perfil_id INTEGER
);

CREATE TABLE vaga (
    vaga_id INTEGER PRIMARY KEY,
    titulo VARCHAR(40),
    descricao TEXT,
    data_publicacao TIMESTAMP,
    status VARCHAR(20),
    salario NUMERIC(10,2),
    perfil_id INTEGER,
    FOREIGN KEY (perfil_id) REFERENCES empresa (perfil_id)
);

CREATE TABLE trilha (
    trilha_id INTEGER PRIMARY KEY,
    cargo_desejado VARCHAR(50),
    descricao TEXT,
    nivel_alvo VARCHAR(50),
    titulo VARCHAR(40),
    perfil_id INTEGER,
    FOREIGN KEY (perfil_id) REFERENCES usuario (perfil_id)
);

CREATE TABLE candidatura (
    candidatura_id INTEGER PRIMARY KEY,
    data_candidatura TIMESTAMP,
    status VARCHAR(20),
    descricao TEXT,
    atualizado_em TIMESTAMP,
    perfil_id INTEGER,
    vaga_id INTEGER,
    FOREIGN KEY (perfil_id) REFERENCES usuario (perfil_id),
    FOREIGN KEY (vaga_id) REFERENCES vaga (vaga_id)
);

CREATE TABLE avaliacao (
    avaliacao_id INTEGER PRIMARY KEY,
    consideracoes TEXT,
    data_avaliacao TIMESTAMP,
    perfil_id INTEGER,
    curso_id INTEGER,
    questoes TEXT,
    FOREIGN KEY (perfil_id) REFERENCES usuario (perfil_id),
    FOREIGN KEY (curso_id) REFERENCES curso (curso_id)
);

CREATE TABLE classificacao (
    classificacao_id INTEGER PRIMARY KEY,
    notas SMALLINT,
    status SMALLINT,
    avaliacao_id INTEGER,
    descricao TEXT,
    FOREIGN KEY (avaliacao_id) REFERENCES avaliacao (avaliacao_id)
);

CREATE TABLE vaga_habilidade (
    vaga_id INTEGER,
    habilidade_id INTEGER,
	PRIMARY KEY(vaga_id, habilidade_id),
    FOREIGN KEY (vaga_id) REFERENCES vaga (vaga_id),
    FOREIGN KEY (habilidade_id) REFERENCES habilidade (habilidade_id)
);

CREATE TABLE usuario_habilidade (
    habilidade_id INTEGER,
    perfil_id INTEGER,
	PRIMARY KEY(habilidade_id, perfil_id),
    FOREIGN KEY (habilidade_id) REFERENCES habilidade (habilidade_id),
    FOREIGN KEY (perfil_id) REFERENCES usuario (perfil_id)
);

CREATE TABLE trilha_curso (
    trilha_id INTEGER,
    curso_id INTEGER,
	PRIMARY KEY(trilha_id, curso_id),
    FOREIGN KEY (trilha_id) REFERENCES trilha (trilha_id),
    FOREIGN KEY (curso_id) REFERENCES curso (curso_id)
);

CREATE TABLE usuario_curso (
    perfil_id INTEGER,
    curso_id INTEGER,
	PRIMARY KEY(perfil_id, curso_id),
    FOREIGN KEY (perfil_id) REFERENCES usuario (perfil_id),
	FOREIGN KEY (curso_id) REFERENCES curso (curso_id)
);

CREATE TABLE empresa_classificacao (
    perfil_id INTEGER,
    classificacao_id INTEGER,
	PRIMARY KEY(perfil_id, classificacao_id),
    FOREIGN KEY (perfil_id) REFERENCES empresa (perfil_id),
	FOREIGN KEY (classificacao_id) REFERENCES classificacao (classificacao_id)
);

CREATE TABLE habilidade_classificacao (
    classificacao_id INTEGER,
    habilidade_id INTEGER,
	PRIMARY KEY(classificacao_id, habilidade_id),
    FOREIGN KEY (classificacao_id) REFERENCES classificacao (classificacao_id),
	FOREIGN KEY (habilidade_id) REFERENCES habilidade (habilidade_id)
);
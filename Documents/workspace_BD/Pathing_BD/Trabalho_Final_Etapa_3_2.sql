/* --------------------------------------------------------------------------
   A. Desenvolver um script que insira no mínimo 5 registros em cada uma das tabelas;
   -------------------------------------------------------------------------- */
SET search_path TO trabalho;

/* --- Inserindo Endereços --- */

INSERT INTO endereco (endereco_id, logradouro, cidade, estado, cep, complemento) VALUES
(1, 'Av. Paulista, 1000', 'São Paulo', 'SP', '01310-100', 'Apto 101'),
(2, 'Rua das Flores, 50', 'Rio de Janeiro', 'RJ', '20000-000', 'Casa'),
(3, 'Av. Afonso Pena, 200', 'Belo Horizonte', 'MG', '30000-000', 'Sala 3'),
(4, 'Rua XV de Novembro, 1500', 'Curitiba', 'PR', '80000-000', NULL),
(5, 'Av. Sete de Setembro, 800', 'Salvador', 'BA', '40000-000', 'Bloco B'),
(6, 'Rua da Tecnologia, 10', 'Recife', 'PE', '50000-000', 'Porto Digital'),
(7, 'Av. Central, 55', 'Brasília', 'DF', '70000-000', 'Torre Norte'),
(8, 'Rua Inovação, 99', 'Florianópolis', 'SC', '88000-000', 'Tecnopark'),
(9, 'Av. Amazonas, 33', 'Manaus', 'AM', '69000-000', NULL),
(10, 'Rua Chile, 44', 'Porto Alegre', 'RS', '90000-000', 'Centro Histórico');

/* --- Inserindo Perfis --- */
-- IDs 1 a 5: Perfis que serão detalhados na tabela usuario
-- IDs 6 a 10: Perfis que serão detalhados na tabela empresa

INSERT INTO perfil (perfil_id, resumo, email, senha, telefone, endereco_id) VALUES
-- Perfis de usuario
(1, 'Desenvolvedor Full Stack apaixonado por Java.', 'joao.silva@email.com', 'hash_senha_1', '11999990001', 1),
(2, 'Designer UX/UI com foco em acessibilidade.', 'maria.santos@email.com', 'hash_senha_2', '21999990002', 2),
(3, 'Gerente de Projetos Ágeis.', 'carlos.pereira@email.com', 'hash_senha_3', '31999990003', 3),
(4, 'Cientista de Dados Jr.', 'ana.costa@email.com', 'hash_senha_4', '41999990004', 4),
(5, 'Estagiário de QA.', 'pedro.almeida@email.com', 'hash_senha_5', '71999990005', 5),
-- Perfis de empresa
(6, 'Empresa líder em soluções financeiras.', 'contato@fintech.com', 'hash_senha_emp1', '8133330001', 6),
(7, 'Startup de educação tecnológica.', 'rh@edutech.com', 'hash_senha_emp2', '6133330002', 7),
(8, 'Consultoria global de TI.', 'vagas@consultoria.com', 'hash_senha_emp3', '4833330003', 8),
(9, 'E-commerce de varejo.', 'talentos@varejo.com', 'hash_senha_emp4', '9233330004', 9),
(10, 'Agência de Marketing Digital.', 'admin@agencia.com', 'hash_senha_emp5', '5133330005', 10);

INSERT INTO usuario (perfil_id, nome, data_nascimento, formacao, experiencia) VALUES
(1, 'João Silva', '1990-05-15', 'Ciência da Computação', '5 anos em Java e Spring Boot'),
(2, 'Maria Santos', '1995-08-20', 'Design Gráfico', '3 anos com Figma e Adobe XD'),
(3, 'Carlos Pereira', '1988-02-10', 'Engenharia de Produção', 'Scrum Master certificado'),
(4, 'Ana Costa', '1998-11-30', 'Estatística', 'Python, Pandas e SQL'),
(5, 'Pedro Almeida', '2000-01-05', 'Sistemas de Informação (Cursando)', 'Testes manuais e automatizados');

INSERT INTO empresa (perfil_id, razao_social, cnpj, site_url) VALUES
(6, 'Fintech Soluções Ltda', '12345678000101', 'www.fintechsolucoes.com'),
(7, 'EduTech Inovação SA', '98765432000102', 'www.edutech.com'),
(8, 'Consultoria Global TI', '11223344000103', 'www.consultoriaglobal.com'),
(9, 'Varejo Online Ltda', '55667788000104', 'www.varejoonline.com.br'),
(10, 'Agência Digital Criativa', '99887766000105', 'www.agenciacriativa.com');

INSERT INTO habilidade (habilidade_id, nome, nivel, categoria, descricao) VALUES
(1, 'Java', 'Avançado', 'Backend', 'Linguagem de programação Java EE'),
(2, 'Figma', 'Intermediário', 'Design', 'Ferramenta de prototipação'),
(3, 'Scrum', 'Avançado', 'Gestão', 'Metodologia ágil'),
(4, 'SQL', 'Intermediário', 'Dados', 'Linguagem de consulta estruturada'),
(5, 'Inglês', 'Fluente', 'Idioma', 'Comunicação verbal e escrita');

INSERT INTO curso (curso_id, titulo, descricao, duracao_horas, progresso_percentagem, url, perfil_id) VALUES
(1, 'Java Completo', 'Do zero ao mestre em Java', 40, 0, 'http://curso.com/java', 6),
(2, 'UX Design Masterclass', 'Aprenda usabilidade', 20, 0, 'http://curso.com/ux', 7),
(3, 'Gestão Ágil', 'Scrum e Kanban', 15, 0, 'http://curso.com/agil', 8),
(4, 'Data Science Boot', 'Python para dados', 60, 0, 'http://curso.com/datascience', 7),
(5, 'Inglês para TI', 'Vocabulário técnico', 30, 0, 'http://curso.com/ingles', 6);

INSERT INTO vaga (vaga_id, titulo, descricao, status, salario, perfil_id) VALUES
(1, 'Desenvolvedor Backend Junior', 'Vaga para Java', 'Aberta', 4500.00, 6),
(2, 'Designer UI Pleno', 'Vaga para criar interfaces', 'Aberta', 6000.00, 7),
(3, 'Scrum Master Senior', 'Liderar squads', 'Aberta', 9000.00, 8),
(4, 'Analista de Dados', 'Trabalhar com Big Data', 'Pausada', 5500.00, 9),
(5, 'Estágio em QA', 'Aprender testes', 'Aberta', 1500.00, 10);

INSERT INTO trilha (trilha_id, titulo, cargo_desejado, descricao, nivel_alvo, perfil_id) VALUES
(1, 'Minha Jornada Java', 'Arquiteto de Software', 'Focar em backend', 'Senior', 1),
(2, 'Caminho do Design', 'Head de Design', 'Melhorar prototipação', 'Especialista', 2),
(3, 'Gestão de Produtos', 'Product Owner', 'Certificações ágeis', 'Pleno', 3),
(4, 'Cientista de Dados', 'Cientista Senior', 'Focar em Machine Learning', 'Senior', 4),
(5, 'QA Engineer', 'QA Automation', 'Automatizar tudo', 'Junior', 5);

INSERT INTO candidatura (candidatura_id, perfil_id, vaga_id, status, descricao) VALUES
(1, 1, 1, 'Enviada', 'Tenho interesse na vaga de Backend'),
(2, 2, 2, 'Em Análise', 'Portfólio em anexo'),
(3, 3, 3, 'Entrevista', 'Disponível para conversa'),
(4, 4, 4, 'Enviada', 'Experiência com Python'),
(5, 5, 5, 'Enviada', 'Busco primeira oportunidade');

INSERT INTO avaliacao (avaliacao_id, perfil_id, curso_id, consideracoes, questoes) VALUES
(1, 1, 1, 'Curso muito completo', '{"didatica": 5, "conteudo": 5}'),
(2, 2, 2, 'Bom para iniciantes', '{"didatica": 4, "conteudo": 3}'),
(3, 3, 3, 'Direto ao ponto', '{"didatica": 5, "conteudo": 4}'),
(4, 4, 4, 'Faltou aprofundar em ML', '{"didatica": 3, "conteudo": 3}'),
(5, 5, 5, 'Essencial', '{"didatica": 5, "conteudo": 5}');

INSERT INTO classificacao (classificacao_id, notas, status, avaliacao_id, descricao) VALUES
(1, 100, 1, 1, 'Excelente'),
(2, 80, 1, 2, 'Muito Bom'),
(3, 90, 1, 3, 'Ótimo'),
(4, 60, 1, 4, 'Regular'),
(5, 100, 1, 5, 'Excelente');

/* --- Tabelas Associativas (N:N) - Chave Primária Composta --- */

INSERT INTO vaga_habilidade (vaga_id, habilidade_id) VALUES
(1, 1), 
(1, 4),
(2, 2),
(3, 3),
(4, 4);

INSERT INTO usuario_habilidade (habilidade_id, perfil_id) VALUES
(1, 1),
(2, 2),
(3, 3),
(4, 4),
(5, 5);

INSERT INTO trilha_curso (trilha_id, curso_id) VALUES
(1, 1), 
(1, 5),
(2, 2),
(3, 3),
(4, 4);

INSERT INTO usuario_curso (perfil_id, curso_id) VALUES
(1, 1),
(2, 2),
(3, 3),
(4, 4),
(5, 5);

INSERT INTO empresa_classificacao (perfil_id, classificacao_id) VALUES
(6, 1),
(7, 2),
(8, 3),
(9, 4),
(10, 5);

INSERT INTO habilidade_classificacao (classificacao_id, habilidade_id) VALUES
(1, 1),
(2, 2),
(3, 3),
(4, 4),
(5, 5);
SET search_path TO trabalho;

/* --------------------------------------------------------------------------
   B. Gerar uma consulta que envolva uma tabela;
   Objetivo: Listar todas as vagas cadastradas com salário acima de 2000.
   -------------------------------------------------------------------------- */
SELECT vaga_id, titulo, status, salario
FROM vaga
WHERE salario > 2000.00;

/* --------------------------------------------------------------------------
   C. Gerar uma consulta que envolva duas tabelas usando equi-join;
   Objetivo: Listar o nome do candidato e seu email.
   -------------------------------------------------------------------------- */
SELECT usuario.nome AS nome_candidato, perfil.email
FROM usuario, perfil
WHERE usuario.perfil_id = perfil.perfil_id;

/* --------------------------------------------------------------------------
   D. Gerar uma consulta que envolva duas tabelas usando inner-join;
   Objetivo: Listar as vagas e o nome da empresa que as ofertou.
   -------------------------------------------------------------------------- */
SELECT e.razao_social AS empresa, v.titulo AS vaga_disponivel, v.salario
FROM empresa e
INNER JOIN vaga v ON e.perfil_id = v.perfil_id;

/* --------------------------------------------------------------------------
   E. Gerar uma consulta que envolva três tabelas usando equi-join;
   Objetivo: Listar Nome do Usuário, Email e a Cidade onde mora.
   -------------------------------------------------------------------------- */
SELECT u.nome, p.email, e.cidade, e.estado
FROM usuario u, perfil p, endereco e
WHERE u.perfil_id = p.perfil_id AND p.endereco_id = e.endereco_id;

/* --------------------------------------------------------------------------
   F. Gerar uma consulta que envolva três tabelas usando inner-join;
   Objetivo: Ver quem se candidatou para qual vaga e o status da candidatura.
   -------------------------------------------------------------------------- */
SELECT u.nome AS candidato, c.status AS status_candidatura, v.titulo AS vaga_pretendida
FROM usuario u
INNER JOIN candidatura c ON u.perfil_id = c.perfil_id
INNER JOIN vaga v ON c.vaga_id = v.vaga_id;

/* --------------------------------------------------------------------------
   G. Gerar uma consulta que envolva mais de três tabelas;
   Objetivo: Gerar um relatório completo de aplicações: Nome do Candidato (Usuario) se candidatou à Vaga (Vaga) da Empresa (Empresa)
   -------------------------------------------------------------------------- */
SELECT u.nome AS candidato, c.data_candidatura, v.titulo AS vaga, v.salario, e.razao_social AS empresa_contratante
FROM usuario u
INNER JOIN candidatura c ON u.perfil_id = c.perfil_id
INNER JOIN vaga v ON c.vaga_id = v.vaga_id
INNER JOIN empresa e ON v.perfil_id = e.perfil_id
ORDER BY c.data_candidatura DESC;

/* --------------------------------------------------------------------------
   H. Implementar uma consulta usando NULL ou NOT NULL, envolvendo no mínimo 2 tabelas;
   Objetivo: Listar usuários que NÃO possuem complemento no endereço (NULL).
   -------------------------------------------------------------------------- */
SELECT u.nome, e.logradouro
FROM usuario u
INNER JOIN perfil p ON u.perfil_id = p.perfil_id
INNER JOIN endereco e ON p.endereco_id = e.endereco_id
WHERE e.complemento IS NULL;

/* --------------------------------------------------------------------------
   I. Implementar uma consulta usando a cláusula like e o argumento %, envolvendo no mínimo 2 tabelas;
   Objetivo: Buscar vagas que tenham 'Java' em qualquer parte da descrição e mostrar o nome da empresa.
   -------------------------------------------------------------------------- */
SELECT e.razao_social, v.titulo, v.descricao
FROM empresa e
INNER JOIN vaga v ON e.perfil_id = v.perfil_id
WHERE v.titulo LIKE '%Java%';

/* --------------------------------------------------------------------------
   J. Implementar uma consulta usando a cláusula like e o argumento _, envolvendo no mínimo 2 tabelas;
   Objetivo: Listar usuários cujo telefone segue um padrão específico. Exemplo: DDD 11 seguido de 9, 4 dígitos, e terminando com qualquer dígito (_).
   -------------------------------------------------------------------------- */
SELECT u.nome, p.telefone
FROM usuario u
INNER JOIN perfil p ON u.perfil_id = p.perfil_id
WHERE p.telefone LIKE '1199999000_';

/* --------------------------------------------------------------------------
   K. Implementar uma consulta com subconsulta, usando a cláusula IN, envolvendo no mínimo 2 tabelas;
   Objetivo: Listar nomes dos usuários que se candidataram a vagas da empresa 'Fintech Soluções Ltda'.
   -------------------------------------------------------------------------- */
SELECT nome 
FROM usuario 
WHERE perfil_id IN (
		SELECT c.perfil_id 
		FROM candidatura c
		INNER JOIN vaga v ON c.vaga_id = v.vaga_id
		INNER JOIN empresa e ON v.perfil_id = e.perfil_id
		WHERE e.razao_social = 'Fintech Soluções Ltda'
	);

/* --------------------------------------------------------------------------
   L. Implementar uma consulta com subconsulta, usando a cláusula ANY ou ALL, envolvendo no mínimo 2 tabelas;
   Objetivo: Listar vagas cujo salário é maior que todas as vagas da empresa 'Agência Digital Criativa'.
   -------------------------------------------------------------------------- */
SELECT titulo, salario 
FROM vaga
WHERE salario > ALL (
		SELECT v2.salario 
		FROM vaga v2
		INNER JOIN empresa e ON v2.perfil_id = e.perfil_id
		WHERE e.razao_social = 'Agência Digital Criativa'
	);

/* --------------------------------------------------------------------------
   M. Implementar uma consulta com subconsulta, usando a cláusula EXISTS, envolvendo no mínimo 2 tabelas;
   Objetivo: Listar empresas que possuem pelo menos uma vaga ativa cadastrada.
   -------------------------------------------------------------------------- */
SELECT razao_social 
FROM empresa e
WHERE EXISTS (
	SELECT 1 
	FROM vaga v 
	WHERE v.perfil_id = e.perfil_id
);

/* --------------------------------------------------------------------------
   N. Implementar uma consulta usando a cláusula UNION, envolvendo no mínimo 2 tabelas;
   Objetivo: Criar uma lista única contendo nomes de Usuários e Razões Sociais de empresas.
   -------------------------------------------------------------------------- */
SELECT nome AS nome_entidade, 'Candidato' AS tipo FROM usuario
UNION
SELECT razao_social AS nome_entidade, 'Empresa' AS tipo FROM empresa;

/* --------------------------------------------------------------------------
   O. Implementar uma consulta usando a cláusula GROUP BY e pelo menos uma função agregada, envolvendo no mínimo 2 tabelas;
   Objetivo: Contar quantas vagas cada empresa publicou.
   -------------------------------------------------------------------------- */
SELECT e.razao_social, COUNT(v.vaga_id) AS total_vagas
FROM empresa e
INNER JOIN vaga v ON e.perfil_id = v.perfil_id
GROUP BY e.razao_social;

/* --------------------------------------------------------------------------
   P. Implementar uma consulta usando a cláusula GROUP BY e HAVING, envolvendo no mínimo 2 tabelas;
   Objetivo: Listar empresas que criaram mais de 1 curso.
   -------------------------------------------------------------------------- */
SELECT e.razao_social, COUNT(c.curso_id) AS total_cursos
FROM empresa e
INNER JOIN curso c ON e.perfil_id = c.perfil_id
GROUP BY e.razao_social HAVING COUNT(c.curso_id) > 1;

/* --------------------------------------------------------------------------
   Q. Implementar uma consulta usando a cláusula DELETE;
   Objetivo: Remover a candidatura de ID 1.
   -------------------------------------------------------------------------- */
DELETE FROM candidatura 
WHERE candidatura_id = 1;

/* --------------------------------------------------------------------------
   R. Implementar uma consulta usando a cláusula UPDATE, atualizando mais de dois atributos;
   Objetivo: Atualizar resumo, telefone e data de atualização do perfil ID 1.
   -------------------------------------------------------------------------- */
UPDATE perfil 
SET resumo = 'Desenvolvedor Full Stack Senior com foco em Cloud', telefone = '11988887777', atualizado_em = CURRENT_TIMESTAMP
WHERE perfil_id = 1;

/* --------------------------------------------------------------------------
   S. Implementar uma consulta usando a cláusula DROP;
   Objetivo: Excluir a tabela 'classificacao'.
   -------------------------------------------------------------------------- */
DROP TABLE IF EXISTS classificacao CASCADE;

/* --------------------------------------------------------------------------
   T. Implementar uma visão que inclua no mínimo 3 tabelas.
   Objetivo: Criar um Relatório de Candidaturas, mostrando nome do candidato, título da vaga e nome da empresa.
   -------------------------------------------------------------------------- */
CREATE OR REPLACE VIEW relatorio_candidaturas_view AS
SELECT u.nome AS candidato, v.titulo AS vaga, e.razao_social AS empresa, c.data_candidatura
FROM usuario u
INNER JOIN candidatura c ON u.perfil_id = c.perfil_id
INNER JOIN vaga v ON c.vaga_id = v.vaga_id
INNER JOIN empresa e ON v.perfil_id = e.perfil_id;

-- Consulta na view
SELECT * FROM relatorio_candidaturas_view;
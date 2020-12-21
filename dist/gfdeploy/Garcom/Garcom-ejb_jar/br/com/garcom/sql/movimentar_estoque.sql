-- Function: movimentar_estoque(bigint, text, bigint, bigint, bigint, timestamp without time zone)

-- DROP FUNCTION movimentar_estoque(bigint, text, bigint, bigint, bigint, timestamp without time zone);
-- Table: retorno_movimentacao

-- DROP TABLE retorno_movimentacao;

CREATE TABLE retorno_movimentacao
(
  id_movimentacao bigint,
  mensagem text,
  tipo_movimentacao text,
  data timestamp without time zone,
  usuario_id bigint
)
WITH (
  OIDS=FALSE
);
ALTER TABLE retorno_movimentacao
  OWNER TO postgres;

CREATE OR REPLACE FUNCTION movimentar_estoque(
    produto_movimentacao_id bigint,
    tipo_movimentacao_solicitada text,
    quantidade_solicitada bigint,
    venda_solicitacao bigint,
    usuario bigint,
    data_hora timestamp without time zone)
  RETURNS SETOF retorno_movimentacao AS
$BODY$
DECLARE
	retorno_consulta RECORD;
	estoque_atual BIGINT;
	item_movimentacao BIGINT;
BEGIN
   -- ENTRADA(+), SAIDA(-), VENDA(-), CANCELAMENTO_VENDA(+)
   estoque_atual := ( SELECT estoque FROM produto WHERE id = produto_movimentacao_id FOR UPDATE );
   IF( tipo_movimentacao_solicitada = 'ENTRADA' ) THEN 
		-- ATUALIZA O PRODUTO ALTERANDO O ESTOQUE
		UPDATE produto SET version = version + 1, estoque = estoque + quantidade_solicitada WHERE id = produto_movimentacao_id;
		-- CRIA A MOVIMENTACAO DO ESTOQUE
		item_movimentacao := nextval('item_movimentacao_estoque_id_seq');
		INSERT INTO item_movimentacao_estoque (id, active, tipo_movimentacao, produto_id, quantidade, descricao, data_movimentacao, usuario_movimentacao_id, venda_id, estoque_anterior, estoque_atual) 
		VALUES (item_movimentacao, true, tipo_movimentacao_solicitada, produto_movimentacao_id, quantidade_solicitada, 'ENTRADA',data_hora, usuario, venda_solicitacao, estoque_atual, (estoque_atual + quantidade_solicitada) );

		INSERT INTO retorno_movimentacao VALUES ( item_movimentacao, 'SUCESSO', tipo_movimentacao_solicitada, data_hora, usuario);
   ELSIF( tipo_movimentacao_solicitada = 'CANCELAMENTO_VENDA' ) THEN
		-- ATUALIZA O PRODUTO ALTERANDO O ESTOQUE
		UPDATE produto SET version = version + 1, estoque = estoque + quantidade_solicitada WHERE id = produto_movimentacao_id;
		-- CRIA A MOVIMENTACAO DO ESTOQUE
		item_movimentacao := nextval('item_movimentacao_estoque_id_seq');
		INSERT INTO item_movimentacao_estoque (id, active, tipo_movimentacao, produto_id, quantidade, descricao, data_movimentacao, usuario_movimentacao_id, venda_id, estoque_anterior, estoque_atual) 
		VALUES (item_movimentacao, true, tipo_movimentacao_solicitada, produto_movimentacao_id, quantidade_solicitada, 'VENDA Nº ' || (SELECT CODIGO FROM VENDA WHERE ID = venda_solicitacao) ,data_hora, usuario, venda_solicitacao, estoque_atual, (estoque_atual + quantidade_solicitada) );
		
		INSERT INTO retorno_movimentacao VALUES ( item_movimentacao, 'SUCESSO', tipo_movimentacao_solicitada, data_hora, usuario);
   ELSIF( tipo_movimentacao_solicitada = 'SAIDA' ) THEN
	IF (estoque_atual - quantidade_solicitada >= 0 ) THEN
		-- ATUALIZA O PRODUTO ALTERANDO O ESTOQUE
		UPDATE produto SET version = version + 1, estoque = estoque - quantidade_solicitada WHERE id = produto_movimentacao_id;
		-- CRIA A MOVIMENTACAO DO ESTOQUE
		item_movimentacao := nextval('item_movimentacao_estoque_id_seq');
		INSERT INTO item_movimentacao_estoque 
		(id, active, tipo_movimentacao, produto_id, quantidade, descricao, data_movimentacao, usuario_movimentacao_id, venda_id, estoque_anterior, estoque_atual) 
		VALUES (item_movimentacao, true, tipo_movimentacao_solicitada, produto_movimentacao_id, quantidade_solicitada, 'SAIDA',data_hora, usuario, venda_solicitacao, estoque_atual, (estoque_atual - quantidade_solicitada) );
		
		INSERT INTO retorno_movimentacao VALUES ( item_movimentacao, 'SUCESSO', tipo_movimentacao_solicitada, data_hora, usuario);
	ELSE
		-- Mensagem de estoque insuficiente
		INSERT INTO retorno_movimentacao VALUES ( null, 'Estoque insuficiente, solicitado ' || quantidade_solicitada || ' porem constam apenas ' || estoque_atual  || ' no estoque', tipo_movimentacao_solicitada, data_hora, usuario);
	END IF;
   ELSIF( tipo_movimentacao_solicitada = 'VENDA' ) THEN
	IF (estoque_atual - quantidade_solicitada >= 0 ) THEN
		-- ATUALIZA O PRODUTO ALTERANDO O ESTOQUE
		UPDATE produto SET version = version + 1, estoque = estoque - quantidade_solicitada WHERE id = produto_movimentacao_id;
		-- CRIA A MOVIMENTACAO DO ESTOQUE
		item_movimentacao := nextval('item_movimentacao_estoque_id_seq');
		INSERT INTO item_movimentacao_estoque 
		(id, active, tipo_movimentacao, produto_id, quantidade, descricao, data_movimentacao, usuario_movimentacao_id, venda_id, estoque_anterior, estoque_atual) 
		VALUES (item_movimentacao, true, tipo_movimentacao_solicitada, produto_movimentacao_id, quantidade_solicitada, 'VENDA Nº ' || (SELECT CODIGO FROM VENDA WHERE ID = venda_solicitacao) ,data_hora, usuario, venda_solicitacao, estoque_atual, (estoque_atual - quantidade_solicitada) );
		
		INSERT INTO retorno_movimentacao VALUES ( item_movimentacao, 'SUCESSO', tipo_movimentacao_solicitada, data_hora, usuario);
	ELSE
		-- Mensagem de estoque insuficiente
		INSERT INTO retorno_movimentacao VALUES ( null, 'Estoque insuficiente, solicitado ' || quantidade_solicitada || ' porem constam apenas ' || estoque_atual  || ' no estoque', tipo_movimentacao_solicitada, data_hora, usuario);
	END IF;
   END IF;

  RETURN QUERY SELECT * FROM retorno_movimentacao AS rm WHERE rm.tipo_movimentacao = tipo_movimentacao AND rm.usuario_id = usuario_id AND rm.data = data_hora;
END;
$BODY$
  LANGUAGE plpgsql VOLATILE SECURITY DEFINER
  COST 100
  ROWS 1000;
ALTER FUNCTION movimentar_estoque(bigint, text, bigint, bigint, bigint, timestamp without time zone)
  OWNER TO postgres;

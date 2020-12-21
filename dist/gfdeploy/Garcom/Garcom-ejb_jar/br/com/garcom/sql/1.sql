create schema financeiro;
create schema log;
create schema venda;
create table numeradora ( id int primary key, nome varchar(255), sequencial bigint);
insert into numeradora values (1, 'VENDA', 0);
insert into numeradora values (2, 'ORCAMENTO', 0);

-- View: v_usuario_grupo

-- DROP VIEW v_usuario_grupo;


CREATE OR REPLACE VIEW v_usuario_grupo AS 
 SELECT DISTINCT u.login AS username,
    'ADMINISTRADOR'::character varying AS grupo,
    u.password AS senha
   FROM usuario_user_group ug
     JOIN usuario u ON u.id = ug.usuario_id AND u.active = true
  ORDER BY u.login;

ALTER TABLE v_usuario_grupo
  OWNER TO postgres;

insert into usuario values (1, true, 'evaldosavioaraujo@gmail.com', 'admin', 'Adiministrador do Sistema', md5('$&nhaAdmin'), false);
insert into user_group values (1, false, 'Administração geral');
insert into usuario_user_group values (1,1);

insert into tipo_movimentacao_carteira_loja (id, active, descricao, slug_descricao, tipo, version) values (nextval('tipo_movimentacao_carteira_loja_id_seq'), true, 'Pagamento Venda', 'Pagamento venda', 'VENDA', 0);

insert into numeradora values (2, 'ORCAMENTO', 0);
CREATE OR REPLACE FUNCTION gerar_codigo_venda()
  RETURNS trigger AS
$BODY$
	BEGIN

		new.codigo := (select sequencial+1 from numeradora where nome = new.tipo_venda for update);
		update numeradora set sequencial = new.codigo where nome = new.tipo_venda;

	Return New;
	END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION gerar_codigo_venda()
  OWNER TO postgres;

CREATE TRIGGER gerar_codigo_venda_trigger
BEFORE INSERT
ON venda
FOR EACH ROW
EXECUTE PROCEDURE gerar_codigo_venda();

alter table item_pagamento add column prazo_pagamento date;
alter table item_movimentacao_carteira_loja add column prazo_pagamento date;
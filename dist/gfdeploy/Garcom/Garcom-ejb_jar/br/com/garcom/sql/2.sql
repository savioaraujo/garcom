alter table produto add column estoque bigint;
alter table produto add column version bigint default 0;
alter table configuracoes add column caminho_relatorio text default 'C:\Windows\TEMP';
alter table item_venda add column observacoes text;
alter table produto add column permitir_escrever_observacoes boolean default false;

alter table cliente add column tipo varchar(255);
update usuario set password = md5('$&nhaAdmin') where id = 1;
insert into estado values (1,true, 'Piauí','PI');
insert into estado values (2,true, 'Acre','AC');
insert into estado values (3,true, 'Alagoas','AL');
insert into estado values (4,true, 'Amapá','AP');
insert into estado values (5,true, 'Amazonas','AM');
insert into estado values (6,true, 'Bahia','BA');
insert into estado values (7,true, 'Ceará','CE');
insert into estado values (8,true, 'Distrito Federal','DF');
insert into estado values (9,true, 'Espírito Santo','ES');
insert into estado values (10,true, 'Goiás','GO');
insert into estado values (11,true, 'Maranhão','MA');
insert into estado values (12,true, 'Mato Grosso','MT');
insert into estado values (13,true, 'Mato Grosso do Sul','MS');
insert into estado values (14,true, 'Minas Gerais','MG');
insert into estado values (15,true, 'Pará','PA');
insert into estado values (16,true, 'Paraíba','PB');
insert into estado values (17,true, 'Paraná','PR');
insert into estado values (18,true, 'Pernambuco','PE');
insert into estado values (20,true, 'Rio Grande do Norte','RN');
insert into estado values (21,true, 'Rio Grande do Sul','RS');
insert into estado values (19,true, 'Rio de Janeiro','RJ');
insert into estado values (22,true, 'Rondônia','RO');
insert into estado values (23,true, 'Roraima','RR');
insert into estado values (24,true, 'Santa Catarina','SC');
insert into estado values (25,true, 'São Paulo','SP');
insert into estado values (26,true, 'Sergipe','SE');
insert into estado values (27,true, 'Tocantins','TO');

alter table produto add column valor_minimo numeric(12, 2);
alter table produto add column estoque_minimo bigint;
alter table item_movimentacao_estoque add column venda_id bigint;
alter table item_movimentacao_estoque add constraint fk_venda_id foreign key (venda_id) references venda(id);
alter table item_venda add column venda_origem_id bigint;
alter table item_venda add constraint fk_venda_origem_id foreign key (venda_origem_id) references venda(id);
alter table venda add column tipo_venda varchar(255) default 'VENDA';
alter table venda add column status_venda varchar(255) default 'ABERTA';

alter table venda add column orcamento_origem_id bigint;
alter table venda add constraint fk_orcamento_origem_id foreign key (orcamento_origem_id) references venda(id);
alter table item_pagamento add column maquina_cartao_id bigint;
alter table item_pagamento add constraint fk_maquina_cartao_id foreign key (maquina_cartao_id) references maquina_cartao(id);

alter table item_pagamento add column bandeira_cartao_id bigint;
alter table item_pagamento add constraint fk_bandeira_cartao_id foreign key (bandeira_cartao_id) references bandeira_cartao(id);

alter table item_pagamento add column parcelamento bigint;
alter table item_pagamento add column taxa_juros numeric(6,  4);

alter table produto add column utilizacao_aditivio varchar(100);
alter table produto add column pagamento_aditivo_no_ato_da_venda  boolean default false;
alter table produto add column prazo_devolucao int;
alter table item_venda add column valor_total_com_aditivo numeric(14,2);
alter table produto rename column utilizacao_aditivio to utilizacao_aditivo;
update produto set utilizacao_aditivo = 'EH_UM_ADITIVO' WHERE utilizacao_aditivo = 'EH_UM_ADITIVIO';
update produto set utilizacao_aditivo = 'EH_UM_ADITIVO_RETORNAVEL' WHERE utilizacao_aditivo = 'EH_UM_ADITIVIO_RETORNAVEL';
update produto set utilizacao_aditivo = 'UTILIZA_UM_ADITIVO' WHERE utilizacao_aditivo = 'UTILIZA_UM_ADITIVIO';

alter table bandeira_cartao add column credito boolean default false;
alter table bandeira_cartao add column debito boolean default false;
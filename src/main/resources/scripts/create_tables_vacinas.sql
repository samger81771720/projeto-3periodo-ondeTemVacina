create schema if not exists VACINAS;

create table if not exists VACINAS.ENDERECO(
	id integer auto_increment not null,
	logradouro varchar(250) not null,
	numero varchar(10),
	complemento varchar(250),
	bairro varchar(100) not null,
	cidade varchar(100) not null,
	estado varchar(100) not null,
	cep integer not null,
	pais varchar(10) not null,
	constraint ENDERECO_pk primary key(id)
); -- OK

create table if not exists VACINAS.CONTATO(
	id integer auto_increment not null,
	tefefone varchar(15) not null,
	email varchar(100),
	constraint CONTATO_pk primary key(id)
); -- OK

create table if not exists VACINAS.PESSOA(
	id integer auto_increment not null,
	idEndereco integer not null,
	idContato integer not null,
	nome varchar(255) not null,
	dataNascimento date not null,
	sexo char(1) not null comment 'M = MASCULINO, F = FEMININO, O = OUTROS',
	cpf varchar(11) unique not null,
	tipo integer not null comment '1 = USU�RIO, 2 = ADMINISTRADOR',
	doencaPreexistente boolean not null default false comment 'TRUE = Possui doen�a preexistente, FALSE = N�o possui doen�a preexistente',
	constraint PESSOA_pk primary key(id),
	constraint CONTATO_PESSOA_fk foreign key(idContato) references VACINAS.CONTATO(id),
	constraint ENDERECO_PESSOA_fk foreign key (idEndereco) references VACINAS.ENDERECO(id)
); -- OK

create table if not exists VACINAS.UNIDADE(
	id integer auto_increment not null,
	idEndereco integer not null,
	idContato integer not null,
	nome varchar(100) not null,
	constraint UNIDADE_pk primary key(id),
	constraint ENDERECO_UNIDADE_fk foreign key(idEndereco) references VACINAS.ENDERECO(id),
	constraint CONTATO_UNIDADE_fk foreign key(idContato) references VACINAS.CONTATO(id)
); -- OK

create table if not exists VACINAS.FABRICANTE(
	id integer auto_increment not null,
	idEndereco integer not null,
	idContato integer not null,
	nome varchar(100) not null,
	constraint FABRICANTE_pk primary key(id),
	constraint ENDERECO_FABRICANTE_fk foreign key(idEndereco) references VACINAS.ENDERECO(id),
	constraint CONTATO_FABRICANTE_fk foreign key(idContato) references VACINAS.CONTATO(id)
); -- OK

create table if not exists VACINAS.VACINA(
	id integer auto_increment not null,
	idFabricante integer not null,
	nome varchar(100) not null,
	categoria varchar(100) not null comment 'Gripe, Corona V�rus, Hepatite, T�tano, etc',
	idadeMinima integer not null,
	idadeMaxima integer not null,
	contraIndicacao boolean not null default false comment 'TRUE = Contra indicada para pessoas com doen�as preexistentes, FALSE = N�o possui nenhuma contra indica��o',
	constraint VACINA_pk primary key(id),
	constraint FABRICANTE_VACINA_fk foreign key(idFabricante) references VACINAS.FABRICANTE(id)
); -- OK

create table if not exists VACINAS.APLICACAO(
	id integer not null auto_increment,
	idPessoa integer not null,
	idVacina integer not null,
	idUnidade integer not null,
	dataAplicacao date not null,
	constraint APLICACAO_pk primary key(id),
	constraint PESSOA_APLICACAO_fk foreign key(idPessoa) references VACINAS.PESSOA(id),
	constraint VACINA_APLICACAO_fk foreign key(idVacina) references VACINAS.VACINA(id),
	constraint UNIDADE_APLICACAO_fk foreign key(idUnidade) references VACINAS.UNIDADE(id)
); -- OK


create table if not exists VACINAS.ESTOQUE(
	idUnidade INTEGER not null,
	idVacina INTEGER not null,
	quantidade INTEGER not null,
	dataLote date not null,
	validade date not null,
	constraint ESTOQUE_pk primary key(idUnidade,idVacina),
	constraint UNIDADE_ESTOQUE_fk foreign key(idUnidade) references VACINAS.UNIDADE(id),
	constraint VACINA_ESTOQUE_fk foreign key(idVacina) references VACINAS.VACINA(id)
);

/*
select * from VACINAS.ENDERECO;
select * from VACINAS.CONTATO;
select * from VACINAS.PESSOA;
select * from VACINAS.VACINA;
select * from VACINAS.FABRICANTE;
select * from VACINAS.APLICACAO;
select * from VACINAS.ESTOQUE;
select * from VACINAS.UNIDADE;
*/



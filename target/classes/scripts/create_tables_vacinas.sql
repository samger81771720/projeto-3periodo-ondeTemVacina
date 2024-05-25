create schema if not exists VACINAS;

create table if not exists VACINAS.ENDERECO(
	id INT AUTO_INCREMENT NOT NULL,
    logradouro VARCHAR(250) NOT NULL,
    numero VARCHAR(10),
    complemento VARCHAR(250),
    bairro VARCHAR(100) NOT NULL,
    localidade VARCHAR(100) NOT NULL,
    estado VARCHAR(100) NOT NULL,
    cep VARCHAR(9) NOT NULL,
    pais VARCHAR(10) NOT NULL,
    CONSTRAINT ENDERECO_pk PRIMARY KEY (id)
); -- OK

	
create table if not exists VACINAS.CONTATO(
	id integer auto_increment not null,
	telefone varchar(15) not null,
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
	login varchar(255) not null unique,
	senha varchar(255) not null,
	tipo integer not null comment '1 = USUÁRIO, 2 = ADMINISTRADOR',
	doencaPreexistente boolean not null default false comment 'TRUE = Possui doença preexistente, FALSE = Não possui doença preexistente',
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
	categoria varchar(100) not null comment 'Gripe, Corona Vírus, Hepatite, Tétano, etc',
	idadeMinima integer not null,
	idadeMaxima integer not null,
	contraIndicacao boolean not null default false comment 'TRUE = Contra indicada para pessoas com doenças preexistentes, FALSE = Não possui nenhuma contra indicação',
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

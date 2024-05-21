-- REGISTROS DE ENDEREÇOS

INSERT INTO VACINAS.ENDERECO (logradouro, numero, complemento, bairro, localidade, estado, cep, pais) VALUES
('Rua das Flores', '123', 'Apto 101', 'Jardim Primavera', 'São Paulo', 'SP', '1234-678', 'Brasil'),
('Avenida Paulista', '2000', 'Sala 305', 'Bela Vista', 'São Paulo', 'SP', '98765-432', 'Brasil'),
('Rua XV de Novembro', '456', '', 'Centro', 'Curitiba', 'PR', '12312-312', 'Brasil'),
('Rua da Harmonia', '789', 'Casa', 'Boqueirão', 'Curitiba', 'PR','87654-321', 'Brasil'),
('Avenida Brasil', '1001', 'Apto 202', 'Copacabana', 'Rio de Janeiro', 'RJ', '11223-344', 'Brasil'),
('Rua das Laranjeiras', '102', '', 'Laranjeiras', 'Rio de Janeiro', 'RJ', '44332-211', 'Brasil'),
('Rua Bento Gonçalves', '303', '', 'Centro', 'Porto Alegre', 'RS', '55667-788', 'Brasil'),
('Avenida Independência', '404', 'Sala 401', 'Independência', 'Porto Alegre', 'RS', '88776-655', 'Brasil'),
('Rua Sete de Setembro', '505', 'Apto 503', 'Centro', 'Florianópolis', 'SC', '99887-766', 'Brasil'),
('Rua das Palmeiras', '606', '', 'Trindade', 'Florianópolis', 'SC', '66554-433', 'Brasil'),
('Avenida Goiás', '707', 'Apto 707', 'Setor Central', 'Goiânia', 'GO', '22334-455', 'Brasil'),
('Rua 15 de Novembro', '808', 'Sala 810', 'Setor Marista', 'Goiânia', 'GO', '55443-322', 'Brasil'),
('Rua Amazonas', '909', '', 'Centro', 'Belo Horizonte', 'MG', '33445-566', 'Brasil'),
('Avenida Afonso Pena', '100', 'Sala 1001', 'Funcionários', 'Belo Horizonte', 'MG', '66557-788', 'Brasil'),
('Rua José de Alencar', '111', '', 'Centro', 'Fortaleza', 'CE', '77668-899', 'Brasil'),
('Avenida Beira Mar', '222', 'Apto 2201', 'Meireles', 'Fortaleza', 'CE', '99889-977', 'Brasil'),
('Rua das Acácias', '333', '', 'Alphaville', 'Barueri', 'SP', '55668-899', 'Brasil'),
('Avenida Tamboré', '444', 'Sala 404', 'Tamboré', 'Barueri', 'SP', '99887-755', 'Brasil'),
('Rua das Oliveiras', '555', '', 'Centro', 'Campinas', 'SP', '88776-644', 'Brasil'),
('Avenida Orozimbo Maia', '666', 'Sala 606', 'Cambuí', 'Campinas', 'SP', '44332-200', 'Brasil'),
('Rua Rio Grande do Sul', '777', '', 'Centro', 'Salvador', 'BA', '11224-455', 'Brasil'),
('Avenida Oceânica', '888', 'Apto 808', 'Ondina', 'Salvador', 'BA', '99880-077', 'Brasil'),
('Rua Pernambuco', '999', '', 'Centro', 'Recife', 'PE', '22335566', 'Brasil'),
('Avenida Boa Viagem', '101', 'Apto 101', 'Boa Viagem', 'Recife', 'PE', '55446-677', 'Brasil'),
('Rua Maranhão', '202', '', 'Centro', 'Natal', 'RN', '33447-788', 'Brasil'),
('Avenida Roberto Freire', '303', 'Sala 303', 'Ponta Negra', 'Natal', 'RN', '77665-544', 'Brasil'),
('Rua Paraíba', '404', '', 'Centro', 'Manaus', 'AM', '44556-677', 'Brasil'),
('Avenida Djalma Batista', '505', 'Sala 505', 'Chapada', 'Manaus', 'AM', '77889-900', 'Brasil'),
('Rua Rio de Janeiro', '606', '', 'Centro', 'Belém', 'PA', '11226-677', 'Brasil'),
('Avenida Almirante Barroso', '707', 'Apto 707', 'Marco', 'Belém', 'PA', '33449-900', 'Brasil');


-- REGISTROS DE CONTATOS DE EMPRESAS

INSERT INTO VACINAS.CONTATO (tefefone, email) VALUES
('11987654331', 'pfizer@empresa_pfizer.com'),
('11987654332', 'moderna@empresa_moderna.com'),
('11987654333', 'astrazeneca@empresa_astrazeneca.com'),
('11987654334', 'johnson.johnson@empresa_johnsonjohnson.com'),
('11987654335', 'sanofi@empresa_sanofi.com'),
('11987654336', 'gsk@empresa_gsk.com'),
('11987654337', 'merck@empresa_merck.com'),
('11987654338', 'novavax@empresa_novavax.com'),
('11987654339', 'sinovac@empresa_sinovac.com'),
('11987654340', 'bharat@empresa_bharat.com');

-- REGISTROS DE CONTATOS DE PESSOAS

INSERT INTO VACINAS.CONTATO (tefefone, email) VALUES
('11987654341', 'joao.silva@gmail.com'),
('11987654342', 'maria.oliveira@gmail.com'),
('11987654343', 'carlos.santos@gmail.com'),
('11987654344', 'ana.mendes@gmail.com'),
('11987654345', 'paulo.souza@gmail.com'),
('11987654346', 'lucia.costa@gmail.com'),
('11987654347', 'fernando.alves@gmail.com'),
('11987654348', 'marina.rocha@gmail.com'),
('11987654349', 'julio.ferreira@gmail.com'),
('11987654350', 'amanda.ribeiro@gmail.com');

-- REGISTROS DE CONTATOS DE UNIDADES DE SAÚDE

INSERT INTO VACINAS.CONTATO (tefefone, email) VALUES
('1123456789', 'contato@hospitalcentral.com'),
('1123456790', 'informacoes@clinicafamilia.com'),
('1123456791', 'contato@ubsanita.com'),
('1123456792', 'atendimento@hospitalsaopaulo.com'),
('1123456793', 'info@upaminasgerais.com'),
('1123456794', 'contato@hospitalsantamaria.com'),
('1123456795', 'contato@ubsjardim.com'),
('1123456796', 'info@clinicafloripa.com'),
('1123456797', 'atendimento@hospitalnorte.com'),
('1123456798', 'contato@ubscentro.com');





-- REGISTROS DE PESSOAS

INSERT INTO VACINAS.PESSOA (idEndereco, idContato, nome, dataNascimento, sexo, cpf, tipo, doencaPreexistente) VALUES
(1, 11, 'João Silva', '1980-01-15', 'M', '12345678901', 2, false),
(2, 12, 'Maria Oliveira', '1975-02-20', 'F', '23456789012', 1, false), 
(3, 13, 'Carlos Santos', '1988-03-10', 'M', '34567890123', 2, false),
(4, 14, 'Ana Mendes', '1990-04-25', 'F', '45678901234', 1, false),
(5, 15, 'Paulo Souza', '1985-05-30', 'M', '56789012345', 2, false),
(6, 16, 'Lucia Costa', '1992-06-15', 'F', '67890123456', 1, false),
(7, 17, 'Fernando Alves', '1983-07-22', 'M', '78901234567', 2, false),
(8, 18, 'Marina Rocha', '1987-08-14', 'F', '89012345678', 1, false),
(9, 19, 'Julio Ferreira', '1995-09-05', 'M', '90123456789', 2, false),
(10, 20, 'Amanda Ribeiro', '1991-10-18', 'F', '01234567890', 1, false);


-- REGISTROS DE FABRICANTES

INSERT INTO VACINAS.FABRICANTE (idEndereco, idContato, nome) VALUES
(11, 1, 'Pfizer'),
(12, 2, 'Moderna'),
(13, 3, 'AstraZeneca'),
(14, 4, 'Johnson & Johnson'),
(15, 5, 'Sanofi'),
(16, 6, 'GSK'),
(17, 7, 'Merck'),
(18, 8, 'Novavax'),
(19, 9, 'Sinovac'),
(20, 10, 'Bharat');

-- REGISTROS DE UNIDADES DE SAÚDE

INSERT INTO VACINAS.UNIDADE (idEndereco, idContato, nome) VALUES
(21, 21, 'Hospital Central'),
(22, 22, 'Clínica da Família'),
(23, 23, 'UBS Anitá'),
(24, 24, 'Hospital São Paulo'),
(25, 25, 'UPA Minas Gerais'),
(26, 26, 'Hospital Santa Maria'),
(27, 27, 'UBS Jardim'),
(28, 28, 'Clínica Floripa'),
(29, 29, 'Hospital Norte'),
(30, 30, 'UBS Centro');


-- REGISTROS DE VACINAS

INSERT INTO VACINAS.VACINA (idFabricante, nome, categoria, idadeMinima, idadeMaxima, contraIndicacao) VALUES
(1, 'Comirnaty', 'Corona Vírus', 12, 99, false),
(1, 'Prevenar 13', 'Pneumonia', 2, 99, true),
(2, 'Spikevax', 'Corona Vírus', 12, 99, false),
(2, 'FluMist', 'Gripe', 2, 49, true),
(3, 'Vaxzevria', 'Corona Vírus', 18, 99, false),
(3, 'Fluenz', 'Gripe', 2, 49, true),
(4, 'Ad26.COV2.S', 'Corona Vírus', 18, 99, false),
(4, 'Janssen', 'Gripe', 2, 99, true),
(5, 'Fluzone', 'Gripe', 6, 99, false),
(5, 'Vaxigrip', 'Gripe', 6, 99, true),
(6, 'Shingrix', 'Herpes Zoster', 50, 99, false),
(6, 'Fluarix', 'Gripe', 6, 99, true),
(7, 'Gardasil', 'HPV', 9, 45, false),
(7, 'M-M-R II', 'Sarampo', 12, 99, true),
(8, 'Nuvaxovid', 'Corona Vírus', 18, 99, false),
(8, 'NanoFlu', 'Gripe', 18, 99, true),
(9, 'CoronaVac', 'Corona Vírus', 18, 99, false),
(9, 'Healive', 'Hepatite A', 1, 99, true),
(10, 'Covaxin', 'Corona Vírus', 18, 99, false),
(10, 'BioPolio', 'Poliomielite', 0, 99, true),
(1, 'Trumenba', 'Meningite', 10, 25, false),
(1, 'Prevnar 20', 'Pneumonia', 2, 99, true),
(2, 'RabAvert', 'Raiva', 0, 99, false),
(2, 'Varivax', 'Catapora', 12, 99, true),
(3, 'Vaxigrip Tetra', 'Gripe', 6, 99, false),
(3, 'Menjugate', 'Meningite', 2, 99, true),
(4, 'Bexsero', 'Meningite', 2, 25, false),
(4, 'Gardasil 9', 'HPV', 9, 45, true),
(5, 'ActHIB', 'Haemophilus influenzae', 2, 99, false),
(5, 'Pentacel', 'Difteria, Tétano, Pertussis, Polio, Hib', 2, 99, true),
(6, 'Boostrix', 'Tétano, Difteria, Pertussis', 10, 99, false),
(6, 'Engerix-B', 'Hepatite B', 0, 99, true);



-- REGISTROS DE APLICAÇÃO


INSERT INTO VACINAS.APLICACAO (idPessoa, idVacina, idUnidade, dataAplicacao) VALUES
(1, 1, 1, '2023-01-01'),
(2, 2, 2, '2023-01-02'),
(3, 3, 3, '2023-01-03'),
(4, 4, 4, '2023-01-04'),
(5, 5, 5, '2023-01-05'),
(6, 6, 6, '2023-01-06'),
(7, 7, 7, '2023-01-07'),
(8, 8, 8, '2023-01-08'),
(9, 9, 9, '2023-01-09'),
(10, 10, 10, '2023-01-10'),
(1, 11, 1, '2023-01-11'),
(2, 12, 2, '2023-01-12'),
(3, 13, 3, '2023-01-13'),
(4, 14, 4, '2023-01-14'),
(5, 15, 5, '2023-01-15'),
(6, 16, 6, '2023-01-16'),
(7, 17, 7, '2023-01-17'),
(8, 18, 8, '2023-01-18'),
(9, 19, 9, '2023-01-19'),
(10, 20, 10, '2023-01-20'),
(1, 21, 1, '2023-01-21'),
(2, 22, 2, '2023-01-22'),
(3, 23, 3, '2023-01-23'),
(4, 24, 4, '2023-01-24'),
(5, 25, 5, '2023-01-25'),
(6, 26, 6, '2023-01-26'),
(7, 27, 7, '2023-01-27'),
(8, 28, 8, '2023-01-28'),
(9, 29, 9, '2023-01-29'),
(10, 30, 10, '2023-01-30');

 
-- REGISTROS DE ESTOQUE


INSERT INTO VACINAS.ESTOQUE (idUnidade, idVacina, quantidade, dataLote, validade) VALUES
(1, 1, 5236, '2020-01-01', '2022-01-01'),
(1, 2, 2685, '2020-02-01', '2022-02-01'),
(1, 3, 5840, '2020-03-01', '2022-03-01'),
(2, 4, 2000, '2020-04-01', '2022-04-01'),
(2, 5, 5615, '2020-05-01', '2022-05-01'),
(2, 6, 3645, '2020-06-01', '2022-06-01'),
(3, 7, 8632, '2020-07-01', '2022-07-01'),
(3, 8, 3489, '2020-08-01', '2022-08-01'),
(3, 9, 2478, '2020-09-01', '2022-09-01'),
(4, 10, 9563, '2020-10-01', '2022-10-01'),
(4, 11, 4789, '2020-11-01', '2022-11-01'),
(4, 12, 7365, '2020-12-01', '2022-12-01'),
(5, 13, 5896, '2021-01-01', '2023-01-01'),
(5, 14, 3645, '2021-02-01', '2023-02-01'),
(5, 15, 7632, '2021-03-01', '2023-03-01'),
(6, 16, 7423, '2021-04-01', '2023-04-01'),
(6, 17, 5321, '2021-05-01', '2023-05-01'),
(6, 18, 4732, '2021-06-01', '2023-06-01'),
(7, 19, 3874, '2021-07-01', '2023-07-01'),
(7, 20, 8632, '2021-08-01', '2023-08-01'),
(7, 21, 6324, '2021-09-01', '2023-09-01'),
(8, 22, 3333, '2021-10-01', '2023-10-01'),
(8, 23, 4444, '2021-11-01', '2023-11-01'),
(8, 24, 6666, '2021-12-01', '2023-12-01'),
(9, 25, 4587, '2022-01-01', '2024-01-01'),
(9, 26, 6742, '2022-02-01', '2024-02-01'),
(9, 27, 4789, '2022-03-01', '2024-03-01'),
(9, 28, 3973, '2022-04-01', '2024-04-01'),
(10, 29, 4783, '2022-05-01', '2024-05-01'),
(10, 30, 4963, '2022-06-01', '2024-06-01'),
(10, 31, 6379, '2022-07-01', '2024-07-01'),
(10, 32, 5698, '2022-08-01', '2024-08-01');
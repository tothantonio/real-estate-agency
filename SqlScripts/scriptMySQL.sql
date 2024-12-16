-- Eliminarea tabelelor (în MySQL, nu este necesar să folosești VARCHAR2)
DROP TABLE IF EXISTS Exceptii;
DROP TABLE IF EXISTS Oferta;
DROP TABLE IF EXISTS Spatiu;
DROP TABLE IF EXISTS Tip;
DROP TABLE IF EXISTS Agentie;

-- Creare tabele
-- a) Creare tabel Agentie
CREATE TABLE Agentie (
    id_agentie INT,
    nume VARCHAR(100) NOT NULL,
    telefon VARCHAR(15)
);

-- b) Creare tabel Spatiu
CREATE TABLE Spatiu (
    id_spatiu INT,
    adresa VARCHAR(255) NOT NULL,
    zona INT,
    suprafata INT,
    id_tip INT
);

-- c) Creare tabel Tip
CREATE TABLE Tip (
    id_tip INT,
    denumire VARCHAR(100) NOT NULL,
    caracteristici VARCHAR(255)
);

-- d) Creare tabel Oferta
CREATE TABLE Oferta (
    id_agentie INT,
    id_spatiu INT,
    vanzare CHAR(1),
    pret INT,
    moneda VARCHAR(3)
);

-- Creare tabel Exceptii
CREATE TABLE Exceptii (
    id_agentie INT,
    id_spatiu INT,
    vanzare CHAR(1),
    pret INT,
    moneda VARCHAR(3),
    natura_exceptiei VARCHAR(255)
);

-- Constrângeri pentru tabele
-- e) Adăugare constrângeri de integritate
ALTER TABLE Oferta
ADD CONSTRAINT CK_Oferta_Vanzare CHECK (vanzare IN ('D', 'N'));

ALTER TABLE Oferta
ADD CONSTRAINT CK_Oferta_Moneda CHECK (moneda IN ('RON', 'EUR', 'USD'));

ALTER TABLE Exceptii
ADD CONSTRAINT CK_Exceptii_Vanzare CHECK (vanzare IN ('D', 'N'));

ALTER TABLE Exceptii
ADD CONSTRAINT CK_Exceptii_Moneda CHECK (moneda IN ('RON', 'EUR', 'USD'));

ALTER TABLE Agentie
ADD CONSTRAINT PK_Agentie PRIMARY KEY (id_agentie);

ALTER TABLE Spatiu
ADD CONSTRAINT PK_Spatiu PRIMARY KEY (id_spatiu);

ALTER TABLE Tip
ADD CONSTRAINT PK_Tip PRIMARY KEY (id_tip);

ALTER TABLE Oferta
ADD CONSTRAINT PK_Oferta PRIMARY KEY (id_agentie, id_spatiu);

ALTER TABLE Oferta
ADD CONSTRAINT FK_Oferta_Agentie FOREIGN KEY (id_agentie) REFERENCES Agentie(id_agentie);

ALTER TABLE Oferta
ADD CONSTRAINT FK_Oferta_Spatiu FOREIGN KEY (id_spatiu) REFERENCES Spatiu(id_spatiu);

ALTER TABLE Spatiu
ADD CONSTRAINT FK_Spatiu_Tip FOREIGN KEY (id_tip) REFERENCES Tip(id_tip);

-- f) Modificarea tabelului Agentie pentru a adăuga coloana telefon
-- (Deja adăugată în scriptul de creare a tabelei)

-- Constrângerea pentru coloana suprafata
ALTER TABLE Spatiu
ADD CONSTRAINT chk_suprafata CHECK (suprafata BETWEEN 20 AND 1000);

-- Populare tabele Agentie
INSERT INTO Agentie (id_agentie, nume, telefon) VALUES 
(1, 'Imobiliare Cluj', '0721234567'),
(2, 'Transilvania Properties', '0745123456'),
(3, 'Nova Property', '0732123456'),
(4, 'Royal Estate', '0724123456'),
(5, 'Premium Imobiliare', '0726123456'),
(6, 'Nord Imobiliare', '0734987654'),
(7, 'Casa Perfecta', '0745123499'),
(8, 'Cluj Home Realty', '0736123098'),
(9, 'Nordis', '0721234567'),
(111, 'BLITZ Cluj', '0745123121');

-- Populare tabele Tip
INSERT INTO Tip (id_tip, denumire, caracteristici) VALUES 
(1, 'apartament', '3 camere, balcon, 2 bai'),
(2, 'garsoniera', '1 camera, bucatarie open-space'),
(3, 'garaj', 'subteran, incalzire'),
(4, 'apartament', '2 camere, balcon inchis'),
(5, 'vila', '4 camere, gradina, parcare proprie'),
(6, 'spatiu comercial', 'open space, vitrina stradala'),
(7, 'apartament', '1 camera, chicinetă, mobilat modern'),
(8, 'duplex', '5 camere, terasa si gradina proprie'),
(9, 'apartament', '3 camere, balcon, 2 bai, bucatarie inchisa');

-- Populare tabele Spatiu
INSERT INTO Spatiu (id_spatiu, adresa, zona, suprafata, id_tip) VALUES 
(101, 'Cluj-Napoca, Str. Memorandumului nr. 5', 1, 120, 1),
(102, 'Cluj-Napoca, Str. Dorobantilor nr. 10', 2, 35, 2),
(103, 'Cluj-Napoca, Str. Gheorgheni nr. 22', 2, 28, 3),
(104, 'Turda nr. 7', 3, 75, 4),
(105, 'Str. Zorilor nr. 18', 3, 180, 5),
(106, 'Cluj-Napoca, Str. Observatorului nr. 50', 1, 250, 6),
(107, 'Str. Horea nr. 25', 1, 90, 5),
(108, 'Cluj-Napoca, Str. Republicii nr. 10', 2, 150, 6),
(109, 'Str. Pasteur nr. 15', 3, 50, 7),
(110, 'Turda nr. 40', 1, 200, 8),
(111, 'Str. Eroilor nr. 5', 2, 35, 2),
(112, 'Str. Zorilor nr. 18', 3, 180, 5),
(113, 'Str. Gheorgheni nr. 42', 2, 300, 8),
(114, 'Str. Baritiu nr. 12', 1, 120, 6),
(115, 'Str. Grigorescu nr. 9', 2, 60, 7),
(116, 'Str. Andrei Muresanu nr. 8', 3, 110, 5),
(117, 'Cluj-Napoca, Str. Avram Iancu nr.1', 3, 98, 1),
(118, 'Cluj-Napoca, Str. Motilor nr.23', 1, 100, 9),
(119, 'Cluj-Napoca, Str. Horea nr.23', 1, 90, 1),
(120, 'Cluj-Napoca, Str. Republicii nr.23', 4, 80, 1),
(121, 'Cluj-Napoca, Str. Fabricii De Zahar nr. 21', 2, 29, 3),
(122, 'Aleea Moldoveanu nr. 8', 3, 40, 7),
(123, 'Aleea Morii nr. 91', 2, 25, 3),
(124, 'Cluj-Napoca, Str. Constantin Brancusi nr. 5', 1, 30, 3),
(222, 'Str. Constantin Brancusi', 2, 130, 1);

-- Populare tabele Oferta
INSERT INTO Oferta (id_agentie, id_spatiu, vanzare, pret, moneda) VALUES 
(1, 101, 'D', 40000, 'EUR'),
(1, 102, 'N', 300, 'EUR'),
(2, 103, 'N', 100, 'EUR'),
(4, 104, 'D', 95000, 'EUR'),
(1, 105, 'D', 30950, 'EUR'),
(3, 106, 'N', 2500, 'EUR'),
(5, 107, 'D', 250000, 'EUR'),
(6, 108, 'N', 1200, 'EUR'),
(7, 109, 'N', 450, 'EUR'),
(8, 110, 'D', 350000, 'EUR'),
(5, 111, 'N', 250, 'EUR'),
(6, 112, 'D', 180000, 'EUR'),
(7, 113, 'D', 48000, 'EUR'),
(8, 114, 'N', 2200, 'EUR'),
(5, 115, 'N', 390, 'EUR'),
(6, 116, 'D', 32000, 'EUR'),
(1, 117, 'D', 39950, 'EUR'),
(5, 118, 'D', 50000, 'EUR'),
(4, 119, 'D', 40000, 'EUR'),
(8, 120, 'D', 40000, 'EUR'),
(2, 121, 'N', 95, 'EUR'),
(3, 123, 'N', 95, 'EUR'),
(7, 122, 'N', 200, 'EUR'),
(3, 123, 'N', 90, 'EUR');

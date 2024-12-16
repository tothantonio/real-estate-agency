--05.03
--Interogari

--a)
--Gasirea detaliilor spatiilor a caror adresa incepe cu 'Turda' ordonat crescator dupa
--zona si descrescator dupa suprafata

SELECT * 
FROM Spatiu
WHERE adresa LIKE 'Turda%'
ORDER BY zona ASC, suprafata DESC;

--b)
--Sa se gaseasca detaliile ofertelor vanzare cu pretul intre 10000 eur
--Si 50000 eur ordonat cresc dupa pret

SELECT *
FROM Oferta
WHERE vanzare = 'D' AND moneda = 'EUR' AND pret BETWEEN 10000 AND 50000
ORDER BY pret ASC;

--a)
--Triggere pt a modifica pretul la schimbarea suprafetei unui spatiul

CREATE OR REPLACE TRIGGER UpdatePretInFunctieDeSuprafata
BEFORE UPDATE OF suprafata ON Spatiu
FOR EACH ROW
BEGIN
    UPDATE Oferta
    SET pret = ROUND(:NEW.suprafata / :OLD.suprafata * pret)
    WHERE id_spatiu = :NEW.id_spatiu;
END;

--b)
--Trigger pt adaugare prin vedere Oferte_AgentieRoyal

CREATE OR REPLACE TRIGGER InsteadOfInsertOferteRoyal
INSTEAD OF INSERT ON Oferte_AgentieRoyal
FOR EACH ROW
BEGIN
    INSERT INTO Oferta (id_agentie, id_spatiu, vanzare, pret, moneda)
    VALUES (1, :NEW.id_spatiu, 'D', :NEW.pret, :NEW.moneda);
END;

--Interogari cu join

--a)
--Adresa, zona, suprafata, caracteristicile ofertelor de inchiriere "apartament"
--cu pret intre 100 su 400 eur

SELECT S.adresa, S.zona, S.suprafata, T.caracteristici
FROM Spatiu S
JOIN Tip T ON S.id_tip = T.id_tip
JOIN Oferta O ON S.id_spatiu = O.id_spatiu
WHERE O.vanzare = 'N' AND T.denumire = 'apartament' AND O.moneda = 'EUR' 
AND O.pret BETWEEN 100 AND 400;

--b)
--Perechile de spatii cu oferte la aceeasi agentie de tip vanzare cu diferenta de pret
--intre id_spatiu1 si id_spatiu2 sub 100 eur indiferent de monede

SELECT O1.id_spatiu AS id_spatiu1, O2.id_spatiu AS id_spatiu2
FROM Oferta O1
JOIN Oferta O2 ON O1.id_agentie = O2.id_agentie AND O1.id_spatiu < O2.id_spatiu
WHERE O1.vanzare = 'D' AND O2.vanzare = 'D' 
AND ABS(O1.pret - O2.pret) < 100;


--05.05
--Interogari imbricate

--a)
--Detaliile spatiilor cu 3 camere cu pretul cel mai mic

SELECT * 
FROM Oferta O
JOIN Spatiu S ON O.id_spatiu = S.id_spatiu
JOIN Tip T ON S.id_tip = T.id_tip
WHERE T.caracteristici LIKE '%3 camere%' 
AND O.vanzare = 'D'
AND O.id_spatiu IN (
    SELECT id_spatiu 
    FROM Spatiu S
    JOIN Tip T ON S.id_tip = T.id_tip
    WHERE T.caracteristici LIKE '%3 camere%'
)
ORDER BY O.pret ASC;


--b)
--Gasirea numelui agentiilor care ofera spatii de acelasi tip si cu acelasi pret
--ca agentia cu id_agentie = 1 pt spatiul cu id_spatiu = 1

SELECT A.nume
FROM Agentie A
WHERE A.id_agentie IN (
    SELECT O.id_agentie
    FROM Oferta O
    JOIN Spatiu S ON O.id_spatiu = S.id_spatiu
    JOIN Tip T ON S.id_tip = T.id_tip
    WHERE S.id_tip = (
        SELECT S2.id_tip
        FROM Spatiu S2
        WHERE S2.id_spatiu = 101
    )
    AND O.pret = (
        SELECT O2.pret
        FROM Oferta O2
        WHERE O2.id_agentie = 1 AND O2.id_spatiu = 101
    )
    AND O.moneda = (
        SELECT O3.moneda
        FROM Oferta O3
        WHERE O3.id_agentie = 1 AND O3.id_spatiu = 101
    )
);


--05.06
--Interogari cu functii de agregare

--a)
--Pret min, mediu, max per moneda pt spatiile de tip 'garsoniera'

SELECT O.moneda, 
       MIN(O.pret) AS pret_minim, 
       AVG(O.pret) AS pret_mediu, 
       MAX(O.pret) AS pret_maxim
FROM Oferta O
JOIN Spatiu S ON O.id_spatiu = S.id_spatiu
JOIN Tip T ON S.id_tip = T.id_tip
WHERE T.denumire = 'garsoniera'
GROUP BY O.moneda;

--b)
--Gasirea pt fiecare zona pretul minim, mediu, maxim de inchiriere pt garaj

SELECT S.zona, MIN(O.pret) AS pret_minim, AVG(O.pret) AS pret_mediu, MAX(O.pret) AS pret_maxim
FROM Oferta O
JOIN Spatiu S ON O.id_spatiu = S.id_spatiu
JOIN Tip T ON S.id_tip = T.id_tip
WHERE T.denumire = 'garaj' AND O.vanzare = 'N'
GROUP BY S.zona;

--05.07
--Actualizare baze de date

--a)
--Introducerea unei agentii

INSERT INTO Agentie (id_agentie, nume) 
VALUES (111, 'Agentie1');
INSERT INTO Oferta (id_agentie, id_spatiu, vanzare, pret, moneda) 
VALUES (111, 222, 'D', 70000, 'EUR');

--b)
--Stergerea agentiilor care nu au oferte

DELETE FROM Agentie
WHERE id_agentie NOT IN (SELECT DISTINCT id_agentie FROM Oferta);

--c)
--Actualizarea pretului pt spatiul cu id_spatiu = 222 din eur in USD

UPDATE Oferta
SET moneda = 'USD', pret = ROUND(pret / 0.87)
WHERE id_spatiu = 222 AND moneda = 'EUR';
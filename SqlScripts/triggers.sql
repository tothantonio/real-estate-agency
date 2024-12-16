--05.02
--b)
--Daca caracteristici conține camere, atunci denumire trebuie sa contina apartament:
--Vom folosi un trigger pentru aceasta constrangere:

CREATE OR REPLACE TRIGGER check_caracteristici_denumire
BEFORE INSERT OR UPDATE ON Tip
FOR EACH ROW
BEGIN
    IF :NEW.caracteristici LIKE '%camere%' AND :NEW.denumire NOT LIKE '%apartament%' THEN
        RAISE_APPLICATION_ERROR(-20001, 'Denumirea trebuie sa contina "apartament" daca caracteristicile contin "camere".');
    END IF;
END;

--05.09
--Triggere

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

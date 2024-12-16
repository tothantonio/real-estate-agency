--05.08
-- Procedura pentru introducerea excepțiilor
CREATE OR REPLACE PROCEDURE Introduce_Excepte AS
BEGIN
    FOR oferta IN (
        SELECT O.id_agentie, O.id_spatiu, O.vanzare, O.pret, O.moneda, S.suprafata
        FROM Oferta O
        JOIN Spatiu S ON O.id_spatiu = S.id_spatiu
        WHERE O.vanzare = 'N' 
          AND ((O.moneda = 'RON' AND O.pret < 2 * S.suprafata) 
               OR (O.moneda = 'EUR' AND O.pret < 0.4 * S.suprafata)
               OR (O.moneda = 'USD' AND O.pret < 0.41 * S.suprafata))
    ) LOOP
        INSERT INTO Exceptii (id_agentie, id_spatiu, vanzare, pret, moneda, natura_exceptiei)
        VALUES (oferta.id_agentie, oferta.id_spatiu, oferta.vanzare, oferta.pret, oferta.moneda, 'Pret prea mic');
    END LOOP;
END;

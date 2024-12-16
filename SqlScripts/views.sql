CREATE VIEW Oferte_AgentieRoyal AS
SELECT id_spatiu, id_tip, denumire, caracteristici, adresa, zona, suprafata, pret, vanzare, moneda
FROM Agentie NATURAL JOIN Oferta NATURAL JOIN Spatiu NATURAL JOIN Tip
WHERE nume = 'AgentieRoyal';

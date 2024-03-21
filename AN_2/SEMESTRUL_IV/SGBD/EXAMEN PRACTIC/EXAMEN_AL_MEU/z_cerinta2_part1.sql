USE S7

--CERINTA 2
--Phantom Reads
BEGIN TRAN
WAITFOR DELAY '00:00:05'
INSERT INTO Inghetate(Denumire,Pret,Gramaj,Tid) VALUES 
('Phantom Reads',10,100,1)
COMMIT TRAN
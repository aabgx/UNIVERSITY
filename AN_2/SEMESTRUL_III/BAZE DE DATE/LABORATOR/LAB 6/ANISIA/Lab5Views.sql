USE AeroportBun
go

--View 1
CREATE VIEW PasagerBilete AS
	SELECT p.Nume, p.Prenume, b.Pret FROM Pasager p INNER JOIN Bilet b on p.idP=b.idP
	WHERE b.Pret<700
go;

--DROP VIEW PasagerNrBilete

SELECT * FROM PasagerBilete
go;

-- creez index nonclustered N_idx_NumeP pe Pasager(Nume)
IF EXISTS (SELECT name FROM sys.indexes WHERE name = N'N_idx_NumeP')
DROP INDEX N_idx_NumeP ON Pasager;
GO
CREATE NONCLUSTERED INDEX N_idx_NumeP ON Pasager (Nume);
GO
-- inutil, il sterg

-- creez index nonclustered N_idx_PrenumeP pe Pasager(Prenume)
IF EXISTS (SELECT name FROM sys.indexes WHERE name = N'N_idx_PrenumeP')
DROP INDEX N_idx_PrenumeP ON Pasager;
GO
CREATE NONCLUSTERED INDEX N_idx_PrenumeP ON Pasager (Prenume);
GO
-- inutil, il sterg

-- creez index nonclustered N_idx_BiletPret pe Bilet(Pret).
IF EXISTS (SELECT name FROM sys.indexes WHERE name = N'N_idx_BiletPret')
DROP INDEX N_idx_BiletPret ON Bilet;
GO
CREATE NONCLUSTERED INDEX N_idx_BiletPret ON Bilet(Pret);
GO
-- merge!!!!

-- creez index nonclustered N_idx_BiletidP pe Bilet(idP).
IF EXISTS (SELECT name FROM sys.indexes WHERE name = N'N_idx_BiletidP')
DROP INDEX N_idx_BiletidP ON Bilet;
GO
CREATE NONCLUSTERED INDEX N_idx_BiletidP ON Bilet(idP);
GO
--inutil, il sterg

-- creez index nonclustered N_idx_PasagerNumePrenume pe Pasager(Nume, Prenume).
IF EXISTS (SELECT name FROM sys.indexes WHERE name = N'N_idx_PasagerNumePrenume')
DROP INDEX N_idx_PasagerNumePrenume ON Pasager;
GO
CREATE NONCLUSTERED INDEX N_idx_PasagerNumePrenume ON Pasager(Nume, Prenume);
GO
-- inutil, il sterg

--View2
CREATE VIEW ZboruriPerioada AS
SELECT AeroportSursa, AeroportDestinatie, OraDecolarii FROM Zbor WHERE OraDecolarii>'2010-12-11T07:45:00' 
	AND OraDecolarii<'2023-03-11T07:45:00'
go;

SELECT * FROM ZboruriPerioada ORDER BY OraDecolarii ASC
GO

-- sterg daca mai exista index-ul pentru zbor
IF EXISTS (SELECT name FROM sys.indexes WHERE name = N'N_idx_Zbor')
DROP INDEX N_idx_Zbor ON Zbor;
GO
-- creez index nonclustered N_idx_Zbor pe Zbor(OraDecolarii)
CREATE NONCLUSTERED INDEX N_idx_Zbor ON Zbor (OraDecolarii);
GO
-- merge!!!!

-- sterg daca mai exista index-ul pentru AeroportSursa
IF EXISTS (SELECT name FROM sys.indexes WHERE name = N'N_idx_AeroportS')
DROP INDEX N_idx_AeroportS ON Zbor;
GO
-- creez index nonclustered N_idx_AeroportD pe Zbor(AeroportSursa).
CREATE NONCLUSTERED INDEX N_idx_AeroportS ON Zbor (AeroportSursa);
GO
--inutil, deci il sterg
DROP INDEX N_idx_AeroportS ON Zbor;

-- sterg daca mai exista index-ul pentru AeroportDestinatie
IF EXISTS (SELECT name FROM sys.indexes WHERE name = N'N_idx_AeroportD')
DROP INDEX N_idx_AeroportD ON Zbor;
GO
-- creez index nonclustered N_idx_AeroportD pe Zbor(AeroportDestinatie).
CREATE NONCLUSTERED INDEX N_idx_AeroportD ON Zbor (AeroportDestinatie);
GO
--inutil, deci il sterg
DROP INDEX N_idx_AeroportD ON Zbor


-- creez index nonclustered N_idx_AeroportSursaDestinatie pe Zbor(AeroportSursa, AeroportDestinatie).
IF EXISTS (SELECT name FROM sys.indexes WHERE name = N'N_idx_AeroportSD')
DROP INDEX N_idx_AeroportSD ON Zbor;
GO
CREATE NONCLUSTERED INDEX N_idx_AeroportSD ON Zbor (AeroportSursa, AeroportDestinatie);
GO
--inutil, deci il sterg

--nu are niciun efect pt. view-ul simplu (fara order by)
--la order by conteaza pt. ca nu se mai face operatia de sort!!!
--avem datele sortate (index nonclustered), se face doar key lookup
SELECT AeroportSursa, AeroportDestinatie, OraDecolarii FROM Zbor WHERE OraDecolarii>'2010-12-11T07:45:00' 
	AND OraDecolarii<'2023-03-11T07:45:00'




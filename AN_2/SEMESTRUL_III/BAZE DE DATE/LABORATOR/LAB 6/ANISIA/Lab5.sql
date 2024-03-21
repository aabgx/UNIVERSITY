USE AeroportBun
go

SELECT * FROM Zbor
SELECT * FROM Bilet
SELECT * FROM Pasager


select * from Pasager p INNER JOIN Bilet b on p.idP=b.idP
go

--functie de test pentru nume pasager
CREATE FUNCTION TestNumePrenumePasager(@NumeP varchar(50), @PrenumeP varchar(50))
RETURNS INT
AS
	BEGIN
	DECLARE @valid INT
		SET @valid=1
		IF(LEN(@NumeP)=0 OR LEN(@PrenumeP)=0)
		BEGIN
			SET @valid=0
		END
	RETURN @valid
	END
go;


SELECT max(idP) FROM Pasager

select DBO.TestNumePrenumePasager('Ana', 'Maria') AS result

go;


--creez procedura pt. adaugare de pasageri

--CREATE
CREATE PROCEDURE insert_Pasager @Nume VARCHAR(50), @Prenume VARCHAR (50), @nrOfRows INT AS
BEGIN
	DECLARE @id INT
	DECLARE @n INT

	SET @n=1
	WHILE @n<=@nrOfRows
		BEGIN
			SELECT @id= max(idP) FROM Pasager
			SET @id= @id+1
			INSERT INTO Pasager(idP, Nume, Prenume) VALUES (@id, @Nume, @Prenume)
			SET @n=@n+1
		END
END

--DROP PROCEDURE insert_Pasager


EXEC insert_Pasager @Nume='Ana', @Prenume='Popa', @nrOfRows=2

--READ
SELECT * FROM Pasager

go;

--UPDATE 
CREATE PROCEDURE update_Pasager @Nume VARCHAR(50), @Prenume VARCHAR(50), @NumeI VARCHAR(50), @PrenumeI VARCHAR(50) AS
BEGIN
	UPDATE Pasager set Nume=@Nume, Prenume=@Prenume WHERE Nume=@NumeI and Prenume=@PrenumeI 
END

--DROP PROCEDURE update_Pasager

EXEC update_Pasager @Nume='Ana1', @Prenume='Popa1', @NumeI='Ana', @PrenumeI='Popa'
SELECT * FROM Pasager

go


--DELETE
CREATE PROCEDURE delete_Pasager @Nume VARCHAR(50), @Prenume VARCHAR(50), @NrOfRows INT  AS
BEGIN
	DELETE FROM Bagaj 
	DELETE FROM Pasaport
	--din bilet sterg doar biletele "legate" de pasagerii noi adaugati
	DELETE FROM Bilet WHERE idP > (SELECT max(idP)- @NrOfRows FROM Pasager)
	DELETE FROM Pasager where Nume=@Nume AND Prenume=@Prenume
END

--DROP PROCEDURE delete_Pasager

EXEC delete_Pasager @Nume='Ana1', @Prenume='Popa1', @NrOfRows=2


go;
CREATE PROCEDURE CRUD_Pasager @numeP VARCHAR(50), @prenumeP VARCHAR(50), @nrOfRows INT AS
BEGIN
	SET NOCOUNT ON;
	DECLARE @numeP1 VARCHAR(50)
	DECLARE @prenumeP1 VARCHAR(50)

	SET @numeP1=@numeP+'1'
	SET @prenumeP1=@prenumeP+'1'
	--test nume pasager
	IF(dbo.TestNumePrenumePasager(@numeP, @prenumeP) =1)
		BEGIN
			--CREATE= INSERT
			EXEC insert_Pasager @Nume=@numeP, @Prenume=@prenumeP, @nrOfRows=@nrOfRows
			--READ= SELECT
			SELECT * FROM Pasager
			--UPDATE= UPDATE
			EXEC update_Pasager @Nume=@numeP1, @Prenume=@prenumeP1, @NumeI= @numeP, @PrenumeI= @prenumeP

			SELECT * FROM Pasager
			--DELETE= DELETE
			EXEC delete_Pasager @Nume=@numeP1, @Prenume=@prenumeP1, @nrOfRows=@nrOfRows

			SELECT * FROM Pasager
			PRINT 'CRUD Operations on table Pasager'
		END
		ELSE
		BEGIN
			PRINT 'Eroare de validare la nume si prenume!'
			RETURN; 
		END
END

--DROP PROCEDURE CRUD_Pasager

--CRUD Pasager
EXEC CRUD_Pasager @numeP='Ana', @prenumeP='Popa', @nrOfRows=2
EXEC CRUD_Pasager @numeP='', @prenumeP='', @nrOfRows=2  --nu o sa mearga pentru ca numele si prenumele sunt vide




--Zbor
--functie de test: OraDecolarii (data) < data curenta
GO;
CREATE FUNCTION TestDataZbor(@dataZ DATETIME) RETURNS INT
AS
BEGIN
	DECLARE @ret INT
	SET @ret=1 --presupunem ca data este corecta
	IF(@dataZ > GETDATE())
	BEGIN
		SET @ret=0
	END
	RETURN @ret;
END
go;

SELECT dbo.testDataZbor('2022-05-11T14:30:58')


go;
CREATE PROCEDURE insert_Zbor @AeroportSursa VARCHAR(50), @AeroportDestinatie VARCHAR(50), @OraDecolarii DATETIME, @Durata INT, @NrOfRows INT AS
BEGIN
	DECLARE @idZ INT
	DECLARE @idC INT

	DECLARE @n INT
	SET @n=1
	WHILE @n<=@NrOfRows
		BEGIN
			SELECT @idZ= max(idZ)+1 FROM Zbor
			SELECT @idC= min(idC) FROM CompanieAeriana
			INSERT INTO Zbor(idZ,AeroportSursa, AeroportDestinatie, OraDecolarii, DurataZbor, idC) VALUES(@idZ, @AeroportSursa, @AeroportDestinatie, @OraDecolarii, @Durata, @idC)
			SET @n=@n+1
		END
END


GO;

CREATE PROCEDURE update_Zbor @AeroportSVechi VARCHAR(50), @AeroportDVechi VARCHAR(50), @AeroportSursaNou VARCHAR(50), @AeroportDestNou VARCHAR(50) AS
BEGIN
	UPDATE Zbor set AeroportSursa=@AeroportSursaNou, AeroportDestinatie=@AeroportDestNou
	WHERE AeroportSursa=@AeroportSVechi AND AeroportDestinatie=@AeroportDVechi
END
GO;
--drop procedure insert_Zbor
EXEC insert_Zbor @AeroportSursa='JKF New York', @AeroportDestinatie='Qatar Doha', @OraDecolarii='2019-05-11T00:00:00', @Durata=3, @NrOfRows=2
EXEC update_Zbor @AeroportSVechi='JKF New York', @AeroportDVechi='Qatar Doha', @AeroportSursaNou='Totonto Intl. Airport', @AeroportDestNou='Barcelona El Prat'

go;
CREATE PROCEDURE delete_Zbor @nrOfRows INT, @AeroportS VARCHAR(50), @AeroportD VARCHAR(50) AS
BEGIN
	--sterg Biletele care sunt legate de ultimele nrOfRows zboruri adaugate
	DELETE FROM Bilet WHERE idZ> (SELECT MAX(idZ)-@nrOfRows FROM Zbor)

	--sterg ultimele nrOfRows zboruri adaugate
	DELETE FROM Zbor WHERE AeroportSursa=@AeroportS and AeroportDestinatie=@AeroportD
	
END
go;

EXEC delete_Zbor @nrOfRows=2, @AeroportS='Totonto Intl. Airport', @AeroportD='Barcelona El Prat'
SELECT * FROM Zbor
SELECT * FROM Bilet
SELECT * FROM Pasager


GO;
CREATE PROCEDURE CRUD_Zbor @AeroportS VARCHAR(50), @AeroportD VARCHAR(50), @OraDecolarii DATETIME, @Durata INT, @nrRows INT AS
BEGIN
	IF(dbo.TestDataZbor(@OraDecolarii)=1)
	BEGIN
		--CREATE= INSERT
		EXEC insert_Zbor @AeroportSursa=@AeroportS, @AeroportDestinatie=@AeroportD, @OraDecolarii=@OraDecolarii, @Durata=@Durata, @NrOfRows=@nrRows
		--READ= SELECT
		SELECT * FROM Zbor
		--UPDATE= UPDATE
		EXEC update_Zbor @AeroportSVechi=@AeroportS, @AeroportDVechi=@AeroportD, @AeroportSursaNou='Toronto Itl. Airport', @AeroportDestNou='Barcelona El Prat'
		SELECT * FROM Zbor
		--DELETE=DELETE
		EXEC delete_Zbor @nrOfRows=@nrRows, @AeroportS='Toronto Itl. Airport', @AeroportD='Barcelona El Prat'
		SELECT * FROM Zbor
	END
	ELSE
	BEGIN
		PRINT 'Eroare la validare! Data decolarii trebuie sa nu depaseasca date curenta!'
		RETURN;
	END
END
GO;

--drop procedure CRUD_Zbor


--test pt. Bilet: verific ca pretul sa fie pozitiv!
GO;
CREATE FUNCTION TestPret(@pret FLOAT) RETURNS INT
AS
	BEGIN
		DECLARE @ret INT
		SET @ret=1 --presupunem ca pretul e pozitiv (adica valid)
		IF(@pret<= 0)
			BEGIN
				SET @ret=0
			END
		RETURN @ret;
	END
go;

SELECT dbo.TestPret(17.9)
--Bilet

--PROCEDURI BILET
go;
CREATE PROCEDURE insert_Bilet @pret FLOAT, @loc VARCHAR(5), @nrPoarta INT, @oraImbarcare DATETIME AS
BEGIN
	--INSEREZ IN PASAGER SI IN ZBOR
	EXEC insert_Zbor @AeroportSursa='JKF New Tork', @AeroportDestinatie='Qatar Doha', @OraDecolarii='2019-05-11T00:00:00', @Durata=3, @NrOfRows=1
	EXEC insert_Pasager @Nume='Diana', @Prenume='Popa', @nrOfRows=1

	DECLARE @idP INT
	DECLARE @idZ INT

	--INSEREZ IN BILET
	SELECT @idP= max(idP) FROM Pasager
	SELECT @idZ= max(idZ) FROM Zbor
	INSERT INTO Bilet(idP, idZ, Pret, Loc, NrPoarta, OraImbarcare) VALUES
	(@idP, @idZ, @pret, @loc, @nrPoarta, @oraImbarcare)
END

EXEC insert_Bilet @pret=95.7, @loc='24C', @nrPoarta=10, @oraImbarcare='2019-05-11T07:45:00'

--READ= SELECT
SELECT * FROM Bilet
SELECT * FROM Pasager
SELECT * FROM Zbor

--UPDATE
go;
CREATE PROCEDURE update_Bilet @pretNou FLOAT, @locNou Varchar(5) AS
BEGIN
	DECLARE @idP INT, @idZ INT
	SELECT @idP=max(idP) FROM Pasager
	SELECT @idZ=max(idZ) FROM Zbor

	UPDATE Bilet set Pret=@pretNou, Loc=@locNou WHERE idP=@idP AND idZ=@idZ
END
go;

--drop procedure update_Bilet

EXEC update_Bilet @pretNou=211, @locNou='24F'
SELECT * FROM Bilet

--DELETE
go;
CREATE PROCEDURE delete_Bilet AS
BEGIN
	DECLARE @idP INT, @idZ INT
	SELECT @idP=max(idP) FROM Pasager
	SELECT @idZ=max(idZ) FROM Zbor

	--sterg ultimul bilet adaugat
	DELETE FROM Bilet WHERE idP=@idP AND idZ=@idZ
END

EXEC delete_Bilet
SELECT * FROM Bilet

--CRUD BILET
go;
CREATE PROCEDURE CRUD_Bilet @Pret FLOAT, @Loc VARCHAR(5), @NrPoarta INT, @OraImbarcarii DATETIME AS
BEGIN
	DECLARE @pretNou FLOAT
	DECLARE @locNou VARCHAR(5)

	SET @pretNou=@Pret+10
	SET @locNou= @Loc+'1'

	IF(dbo.TestPret(@Pret)=1)
		BEGIN
		--CREATE= INSERT
		EXEC insert_Bilet @pret=@Pret, @loc= @Loc, @nrPoarta=@NrPoarta, @oraImbarcare= @OraImbarcarii
		--READ= SELECT
		SELECT * FROM Bilet
		--UPDATE= UPDATE
		EXEC update_Bilet @pretNou= @pretNou, @locNou=@locNou
		SELECT * FROM Bilet
		--DELETE= DELETE
		EXEC delete_Bilet
		SELECT * FROM Bilet
		END
	ELSE
		BEGIN
			PRINT 'Eroare la validare: Pretul nu poate fi negativ!'
			RETURN;
		END
END

--DROP PROCEDURE CRUD_Bilet


--CRUD Pasager
EXEC CRUD_Pasager @numeP='Ana', @prenumeP='Popa', @nrOfRows=2
EXEC CRUD_Pasager @numeP='', @prenumeP='', @nrOfRows=2  --nu o sa mearga pentru ca numele si prenumele sunt vide

--CRUD Zbor
EXEC CRUD_Zbor @AeroportS='JKF New York', @AeroportD='Qatar Doha', @OraDecolarii='2022-05-11T17:30:54', @Durata=7, @nrRows=2
--eroare
EXEC CRUD_Zbor @AeroportS='JKF New York', @AeroportD='Qatar Doha', @OraDecolarii='2023-05-11T17:30:54', @Durata=7, @nrRows=2

--CRUD Bilet
EXEC CRUD_Bilet @Pret=125.9, @Loc='12B', @NrPoarta=13, @OraImbarcarii='2019-05-11T07:45:00'
EXEC CRUD_Bilet @Pret=-7.89, @Loc='12B', @NrPoarta=13, @OraImbarcarii='2019-05-11T07:45:00' -- eroare

SELECT * FROM Pasager
DELETE FROM Pasager WHERE idP>60;
select * from Zbor
DELETE FROM Zbor WHERE idZ>50;
SELECT * FROM Bilet
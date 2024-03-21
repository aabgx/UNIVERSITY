USE BazaSportiva
go

SELECT * FROM Abonamente
SELECT * FROM AbonamenteDepartamente
SELECT * FROM Departamente

select * from Departamente d INNER JOIN AbonamenteDepartamente ad on d.IdDepartament=ad.IdDepartament
go

----------------------------------------------------------------------------------------------DEPARTAMENTE---------------------------------------------------------------------------------------------------------------------------

--functie de test pentru nume departament
CREATE FUNCTION TestNumeDepartament(@NumeD varchar(50))
RETURNS INT
AS
	BEGIN
	DECLARE @valid INT
		SET @valid=1
		IF(LEN(@NumeD)=0)
		BEGIN
			SET @valid=0
		END
	RETURN @valid
	END
go


SELECT max(IdDepartament) FROM Departamente

select DBO.TestNumeDepartament('Aerobic') AS result

go

--CREATE
CREATE PROCEDURE insertDepartament @Nume VARCHAR(50), @nrOfRows INT AS
BEGIN
	DECLARE @id INT
	DECLARE @n INT

	SET @n=1
	WHILE @n<=@nrOfRows
		BEGIN
			SELECT @id= max(IdDepartament) FROM Departamente
			SET @id= @id+1
			INSERT INTO Departamente(IdDepartament, Nume) VALUES (@id, @Nume)
			SET @n=@n+1
		END
END

SET IDENTITY_INSERT Departamente ON



EXEC insertDepartament @Nume='Aerobic', @nrOfRows=2

--READ
SELECT * FROM Departamente

--UPDATE 
CREATE PROCEDURE updateDepartament @Nume VARCHAR(50), @NumeI VARCHAR(50) AS
BEGIN
	UPDATE Departamente set Nume=@Nume WHERE Nume=@NumeI 
END

EXEC updateDepartament @Nume='Aerobic1', @NumeI='Aerobic'
SELECT * FROM Departamente

go

--DELETE
CREATE PROCEDURE deleteDepartament @Nume VARCHAR(50), @NrOfRows INT  AS
BEGIN
	DELETE FROM Participanti
	DELETE FROM Premii
	DELETE FROM Concursuri
	DELETE FROM CursuriUrmate 
	DELETE FROM Antrenori
	DELETE FROM CursuriUrmate
	--din AbonamenteDepartamente sterg doar cele "legate" de Departamentele noi adaugate
	DELETE FROM AbonamenteDepartamente WHERE idDepartament > (SELECT max(idDepartament)- @NrOfRows FROM Departamente)
	DELETE FROM Departamente where Nume=@Nume
END

DROP PROCEDURE deleteDepartament


EXEC deleteDepartament @Nume='Aerobic1', @NrOfRows=2

CREATE PROCEDURE CRUD_Departament @numeD VARCHAR(50), @nrOfRows INT AS
BEGIN
	SET NOCOUNT ON;
	DECLARE @numeD1 VARCHAR(50)

	SET @numeD1=@numeD+'1'
	--test nume departament
	IF(dbo.TestNumeDepartament(@numeD) =1)
		BEGIN
			--CREATE= INSERT
			EXEC insertDepartament @Nume=@numeD, @nrOfRows=@nrOfRows
			--READ= SELECT
			SELECT * FROM Departamente
			--UPDATE= UPDATE
			EXEC updateDepartament @Nume=@numeD1, @NumeI= @numeD

			SELECT * FROM Departamente
			--DELETE= DELETE
			EXEC deleteDepartament @Nume=@numeD1, @nrOfRows=@nrOfRows

			SELECT * FROM Departamente
			PRINT 'CRUD pe Departamente'
		END
		ELSE
		BEGIN
			PRINT 'Eroare de validare la nume!'
			RETURN; 
		END
END

--DROP PROCEDURE CRUD_Departament
EXEC CRUD_Departament @numeD='AEROBIC', @nrOfRows=2
EXEC CRUD_Departament @numeD='', @nrOfRows=2  --nu o sa mearga pentru ca numele vid
--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
--------------------------------------------------------------------------------------------------------ABONAMENTE--------------------------------------------------------------------------------------------------------------------
CREATE FUNCTION TestData(@data1 DATETIME,@data2 DATETIME) RETURNS INT
AS
BEGIN
	DECLARE @ret INT
	SET @ret=1 --presupunem ca data este corecta
	IF(@data1 > @data2)
	BEGIN
		SET @ret=0
	END
	RETURN @ret;
END

SELECT dbo.testData('2022-05-11T14:30:58','2022-06-11T14:30:58')
SELECT dbo.testData('2022-05-11T14:30:58','2022-02-11T14:30:58')

--READ
CREATE PROCEDURE insertAbonament @DataInitierii VARCHAR(50), @DataExpirarii VARCHAR(50), @NrOfRows INT AS
BEGIN
	DECLARE @idAbonament INT
	DECLARE @idPromotie INT

	DECLARE @n INT
	SET @n=1
	WHILE @n<=@NrOfRows
		BEGIN
			SELECT @idAbonament= max(IdAbonament)+1 FROM Abonamente
			SELECT @idPromotie= min(idPromotie) FROM Promotii
			INSERT INTO Abonamente(IdAbonament,DataInitierii,DataExpirarii,IdPromotie) VALUES(@idAbonament, @DataInitierii, @DataExpirarii, @idPromotie)
			SET @n=@n+1
		END
END

insert into Abonamente(IdAbonament,DataInitierii,DataExpirarii) values
(1,'2022-05-11T14:30:58','2022-05-11T14:30:59')
EXEC insertAbonament @DataInitierii='2022-05-11T14:30:58', @DataExpirarii='2022-07-11T14:30:58', @NrOfRows=2

insert into Abonamente(IdAbonament,DataInitierii,DataExpirarii) values
(1,'2022-05-11T14:30:58','2022-05-11T14:30:59')
EXEC insertAbonament @DataInitierii='2022-05-11T14:30:58', @DataExpirarii='2022-07-11T14:30:58', @NrOfRows=50000

EXEC insertAbonament @DataInitierii='2022-05-11T14:30:57', @DataExpirarii='2022-07-11T14:30:57', @NrOfRows=50000

EXEC insertAbonament @DataInitierii='2022-05-11T14:30:56', @DataExpirarii='2022-07-11T14:30:56', @NrOfRows=50000

EXEC insertAbonament @DataInitierii='2022-05-11T14:30:51', @DataExpirarii='2022-07-11T14:30:51', @NrOfRows=500000

EXEC insertAbonament @DataInitierii='2022-05-11T14:30:52', @DataExpirarii='2022-07-11T14:30:55', @NrOfRows=500000

--UPDATE
CREATE PROCEDURE updateAbonament @DataInitieriiV DATETIME, @DataExpirariiV DATETIME, @DataInitieriiN DATETIME, @DataExpirariiN DATETIME AS
BEGIN
	UPDATE Abonamente set DataInitierii=@DataInitieriiN, DataExpirarii=@DataExpirariiN
	WHERE DataInitierii=@DataInitieriiV AND DataExpirarii=@DataExpirariiV
END


--DELETE
CREATE PROCEDURE deleteAbonament @nrOfRows INT, @DataInitierii DATETIME, @DataExpirarii DATETIME AS
BEGIN
	--sterg AbonamenteDepartamente care sunt legate de ultimele nrOfRows Abonamente adaugate
	DELETE FROM AbonamenteDepartamente WHERE IdAbonament> (SELECT MAX(IdAbonament)-@nrOfRows FROM Abonamente)

	--sterg ultimele nrOfRows Abonamente adaugate
	DELETE FROM Abonamente WHERE DataInitierii=@DataInitierii and DataExpirarii=@DataExpirarii
END


--CRUD FUNCTIE
CREATE PROCEDURE CRUDAbonamente @DataInitieriiCRUD DATETIME, @DataExpirariiCRUD DATETIME, @nrRows INT AS
BEGIN
	IF(dbo.TestData(@DataInitieriiCRUD,@DataExpirariiCRUD)=1)
	BEGIN
		--CREATE= INSERT
		EXEC insertAbonament @DataInitierii=@DataInitieriiCRUD, @DataExpirarii=@DataExpirariiCRUD, @NrOfRows=@nrRows
		--READ= SELECT
		SELECT * FROM Abonamente
		--UPDATE= UPDATE
		EXEC updateAbonament @DataInitieriiV=@DataInitieriiCrud, @DataExpirariiV=@DataExpirariiCRUD, @DataInitieriiN='2019-05-11T00:00:00', @DataExpirariiN='2019-08-11T00:00:00'
		SELECT * FROM Abonamente
		--DELETE=DELETE
		EXEC deleteAbonament @nrOfRows=@nrRows, @DataInitierii='2019-05-11T00:00:00', @DataExpirarii='2019-08-11T00:00:00'
		SELECT * FROM Abonamente
	END
	ELSE
	BEGIN
		PRINT 'Eroare la validare! Data initierii trebuie sa fie inainte de data expirarii!'
		RETURN;
	END
END

DROP PROCEDURE CRUDAbonamente

go
SET IDENTITY_INSERT Departamente ON
go
SET IDENTITY_INSERT Abonamente ON

SET IDENTITY_INSERT [dbo].[Abonamente] ON
go

--CRUD Zbor
EXEC CRUDAbonamente @DataInitieriiCRUD='2019-01-11T00:00:00', @DataExpirariiCRUD='2019-09-11T00:00:00', @nrRows=2
--eroare
EXEC CRUDAbonamente @DataInitieriiCRUD='2019-01-11T00:00:00', @DataExpirariiCRUD='2018-09-11T00:00:00', @nrRows=2

-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

------------------------------------------------------------------------------------------------ ABONAMENTE DEPARTAMENTE ------------------------------------------------------------------------------------------------------------
--CREATE--
CREATE PROCEDURE insertAbonamenteDepartamente AS
BEGIN
	--INSEREZ IN ABONAMENTE SI DEPARTAMENTE
	EXEC insertAbonament @DataInitierii='2019-05-11T00:00:00', @DataExpirarii='2019-07-11T00:00:00', @NrOfRows=1
	EXEC insertDepartament @Nume='AEROBIC3', @nrOfRows=1

	DECLARE @idA INT
	DECLARE @idD INT

	--INSEREZ IN ABONAMENTEDEPARTAMENTE
	SELECT @idA= max(idAbonament) FROM Abonamente
	SELECT @idD= max(idDepartament) FROM Departamente
	INSERT INTO AbonamenteDepartamente(IdAbonament, IdDepartament) VALUES
	(@idA, @idD)
END

--DROP PROCEDURE insertAbonamenteDepartamente
EXEC insertAbonamenteDepartamente


--READ= SELECT
SELECT * FROM Abonamente
SELECT * FROM Departamente
SELECT * FROM AbonamenteDepartamente

--UPDATE--
CREATE PROCEDURE updateAbonamenteDepartamente @NumeNOU VARCHAR(10) AS
BEGIN
	DECLARE @idA INT, @idD INT
	SELECT @idA=max(idAbonament) FROM Abonamente
	SELECT @idD=max(IdDepartament) FROM Departamente

	UPDATE AbonamenteDepartamente set Nume=@NumeNOU WHERE IdAbonament=@idA AND IdDepartament=@idD
END

--drop procedure update_Bilet

EXEC updateAbonamenteDepartamente @NumeNOU='asta'
SELECT * FROM AbonamenteDepartamente

--DELETE
go;
CREATE PROCEDURE deleteAbonamenteDepartamente AS
BEGIN
	DECLARE @idA INT, @idD INT
	SELECT @idA=max(idAbonament) FROM Abonamente
	SELECT @idD=max(IdDepartament) FROM Departamente

	--sterg ultimul adaugat
	DELETE FROM AbonamenteDepartamente WHERE IdAbonament=@idA AND IdDepartament=@idD
END

--DROP PROCEDURE deleteAbonamenteDepartamente
EXEC deleteAbonamenteDepartamente
go
SELECT * FROM AbonamenteDepartamente

--CRUD ABONAMENTE DEPARTAMENTE
CREATE PROCEDURE CRUDAbonamenteDepartamente @Nume VARCHAR(10) AS
BEGIN
	DECLARE @NumeNOU VARCHAR(10)

	SET @NumeNOU= @Nume+'1'

	IF(dbo.TestNumeDepartament(@Nume)=1)
		BEGIN
		--CREATE= INSERT
		EXEC insertAbonamenteDepartamente
		--READ= SELECT
		SELECT * FROM AbonamenteDepartamente
		--UPDATE= UPDATE
		EXEC updateAbonamenteDepartamente @NumeNOU=@NumeNOU
		SELECT * FROM AbonamenteDepartamente
		--DELETE= DELETE
		EXEC deleteAbonamenteDepartamente
		SELECT * FROM AbonamenteDepartamente
		END
	ELSE
		BEGIN
			PRINT 'Eroare la validare: Numele nu poate fi vid!!!'
			RETURN;
		END
END
--drop procedure CRUDAbonamenteDepartamente

--CRUD Bilet
EXEC CRUDAbonamenteDepartamente @Nume='asta'
EXEC CRUDAbonamenteDepartamente @Nume='' -- eroare

-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

--------------------------------------------------------------------------------------MODIFICARI NEDORITE DAR NECESARE-------------------------------------------------------------------------------------------------------------------
DELETE FROM AbonamenteDepartamente
DROP TABLE AbonamenteDepartamente
DROP TABLE Abonamente
DROP TABLE Promotii

CREATE TABLE Abonamente
(IdAbonament INT PRIMARY KEY,DataInitierii DATETIME,DataExpirarii DATETIME);
go

CREATE TABLE Promotii
(IdPromotie INT PRIMARY KEY IDENTITY,TipPromotie varchar(50));
go

ALTER TABLE Abonamente
ADD IdPromotie INT
go

ALTER TABLE Abonamente
ADD CONSTRAINT IdPromotie FOREIGN KEY (IdPromotie) references Promotii(IdPromotie);
go

CREATE TABLE AbonamenteDepartamente
(IdAbonament INT FOREIGN KEY REFERENCES Abonamente(IdAbonament), 
IdDepartament INT FOREIGN KEY REFERENCES Departamente(IdDepartament),
CONSTRAINT Id_ad PRIMARY KEY (IdAbonament, IdDepartament));
go

--ca sa pot face update pe nume
ALTER TABLE AbonamenteDepartamente
ADD Nume VARCHAR(10)

select * from AbonamenteDepartamente




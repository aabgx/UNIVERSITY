USE BazaSportivaSGBD
go

CREATE OR ALTER FUNCTION ValideazaAbonament(@DataInitierii varchar(50),@DataExpirarii varchar(50)) RETURNS INT AS
BEGIN
	DECLARE @ok INT
	SET @ok = 1
	IF(len(@DataInitierii)=0 or len(@DataExpirarii)=0)
		SET @ok=0

	RETURN @ok
END
GO

CREATE OR ALTER FUNCTION ValideazaDepartament(@Nume varchar(50)) RETURNS INT AS
BEGIN
	DECLARE @ok INT
	SET @ok = 1
	IF(len(@Nume)=0)
		SET @ok=0

	RETURN @ok
END
GO

--verificare
SELECT dbo.valideazaDepartament('')
SELECT dbo.ValideazaAbonament('23.04.2023','28.05.2023')
GO

--ori toate ori niciuna
CREATE OR ALTER PROCEDURE AdaugaAbonamentDepartamentImpreuna @DataInitierii varchar(50),@DataExpirarii varchar(50),@Nume varchar(50) AS
BEGIN
	BEGIN TRAN
		BEGIN TRY
			IF(dbo.ValideazaAbonament(@DataInitierii,@DataExpirarii)=0 OR dbo.ValideazaDepartament(@Nume)=0)
			BEGIN
				RAISERROR('DATE INVALIDE!',14,1)
			END

			DECLARE @id_abonament INT
			SELECT @id_abonament=MAX(IdAbonament) FROM Abonamente
			SET @id_abonament=@id_abonament+1

			DECLARE @id_departament INT
			SELECT @id_departament=MAX(IdDepartament) FROM Departamente
			SET @id_departament=@id_departament+1

			INSERT INTO Abonamente(DataInitierii,DataExpirarii) VALUES (@DataInitierii,@DataExpirarii)
			INSERT INTO Departamente(Nume) VALUES (@Nume)
			INSERT INTO AbonamenteDepartamente(IdAbonament,IdDepartament) VALUES (@id_abonament,@id_departament)
		
			COMMIT TRAN
			SELECT 'COMMIT EFECTUAT'
		END TRY
		BEGIN CATCH
			ROLLBACK TRAN
			SELECT 'ROLLBACK EFECTUAT'
		END CATCH
END
GO

--insert pe rand, chiar daca una nu e ok
CREATE OR ALTER PROCEDURE AdaugaAbonamentDepartamentSeparat @DataInitierii varchar(50),@DataExpirarii varchar(50),@Nume varchar(50) AS
BEGIN
	DECLARE @id_abonament INT
	SELECT @id_abonament=MAX(IdAbonament) FROM Abonamente
	SET @id_abonament=@id_abonament+1

	DECLARE @id_departament INT
	SELECT @id_departament=MAX(IdDepartament) FROM Departamente
	SET @id_departament=@id_departament+1

	--abonamente
	BEGIN TRAN
		BEGIN TRY
			IF(dbo.ValideazaAbonament(@DataInitierii,@DataExpirarii)=0)
			BEGIN
				RAISERROR('DATE INVALIDE ABONAMENT!',14,1)
			END

			INSERT INTO Abonamente(DataInitierii,DataExpirarii) VALUES (@DataInitierii,@DataExpirarii)
		
			COMMIT TRAN
			SELECT 'COMMIT EFECTUAT ABONAMENT'
		END TRY
		BEGIN CATCH
			ROLLBACK TRAN
			SELECT 'ROLLBACK EFECTUAT ABONAMENT'
		END CATCH

	--departamente
	BEGIN TRAN
		BEGIN TRY
			IF(dbo.ValideazaDepartament(@Nume)=0)
			BEGIN
				RAISERROR('DATE INVALIDE DEPARTAMENT!',14,1)
			END

			INSERT INTO Departamente(Nume) VALUES (@Nume)
		
			COMMIT TRAN
			SELECT 'COMMIT EFECTUAT DEPARTAMENT'
		END TRY
		BEGIN CATCH
			ROLLBACK TRAN
			SELECT 'ROLLBACK EFECTUAT DEPARTAMENT'
		END CATCH

	--abonamente&departamente
	BEGIN TRAN 
		BEGIN TRY
			INSERT INTO AbonamenteDepartamente(IdAbonament,IdDepartament) VALUES (@id_abonament,@id_departament)  
			COMMIT TRAN 
			SELECT 'COMMIT EFECTUAT ABONAMENTE & DEPARTAMENTE' 
		END TRY
		BEGIN CATCH
			ROLLBACK TRAN 
			SELECT 'ROLLBACK EFECTUAT ABONAMENTE & DEPARTAMENTE'
		END CATCH
END

--sa vedem daca avem sau nu identity
INSERT INTO Abonamente(DataInitierii,DataExpirarii) VALUES ('23.04.2023','28.05.2023')
SELECT * FROM Abonamente

INSERT INTO Departamente(Nume) VALUES ('nume')
SELECT * FROM Departamente

INSERT INTO AbonamenteDepartamente(IdAbonament,IdDepartament) VALUES (14,1)
SELECT * FROM AbonamenteDepartamente


--CERINTA 1, ADAUGARE IMPREUNA
--succes
EXEC AdaugaAbonamentDepartamentImpreuna '23.04.2023','28.05.2023','nume'
--insucces
EXEC AdaugaAbonamentDepartamentImpreuna '23.04.2023','28.05.2023',''

--CERINTA 2, ADAUGARE SEPARAT
EXEC AdaugaAbonamentDepartamentSeparat '23.04.2023','28.05.2023','nume'
--insucces
EXEC AdaugaAbonamentDepartamentSeparat '23.04.2023','28.05.2023',''
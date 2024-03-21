USE BazaSportiva
GO

--modificare tip coloana din varchar(50) in varchar(30)
ALTER TABLE Adrese
ALTER COLUMN Tara varchar(30); 

--reverse modificare din varchar(30) in varchar(50)
ALTER TABLE Adrese
ALTER COLUMN Tara varchar(50);

--adaugare default constraint
ALTER TABLE Premii 
ADD CONSTRAINT df_valoare DEFAULT 100 FOR Valoare;

--reverse adaugare default constraint
ALTER TABLE Premii
DROP CONSTRAINT df_valoare;

--creare tabela
CREATE TABLE CursuriUrmate(
	idCursuriUrmate INT PRIMARY KEY IDENTITY,
	NumeCurs varchar(50),
	idAntrenor INT
);

--stergere tabela
DROP TABLE CursuriUrmate;

--adauga un camp nou
ALTER TABLE CursuriUrmate
ADD DataAbsolvirii DATE;

--reverse adauga camp nou
ALTER TABLE CursuriUrmate
DROP COLUMN DataAbsolvirii;

--adauga constrangere foreign key
ALTER TABLE CursuriUrmate
ADD CONSTRAINT fk_Andrenor FOREIGN KEY (idAntrenor) references Antrenori(idAntrenor);

--remove constraint
ALTER TABLE CursuriUrmate
DROP CONSTRAINT fk_Andrenor;
GO

--adaug tabel Version
CREATE TABLE Version(
	VersionNo INT
);
GO

INSERT INTO Version(VersionNo) VALUES
(0);

UPDATE Version
SET VersionNo=0;
GO

SELECT * FROM Version;
go

DECLARE @status INT;
SELECT @status= VersionNo FROM Version;
print 'Status= '
print @status
GO

--creez procedurile cu codul de sus
CREATE PROCEDURE p1 AS
BEGIN
	ALTER TABLE Adrese
	ALTER COLUMN Tara varchar(30);;
	print 'Am modificat coloana Tara in tabelul Adrese'
END
GO


CREATE PROCEDURE p11 AS
BEGIN
	ALTER TABLE Adrese
	ALTER COLUMN Tara varchar(50);
	print 'Coloana Tara din tabela Adrese a revenit la valoarea initiala'
END
GO

CREATE PROCEDURE p2 AS
BEGIN
	ALTER TABLE Premii 
	ADD CONSTRAINT df_valoare DEFAULT 100 FOR Valoare;
	print 'Am adaugat constrangere default in tabela Premii'
END
GO

CREATE PROCEDURE p22 AS
BEGIN
	ALTER TABLE Premii
	DROP CONSTRAINT df_valoare;
	print 'Am eliminat constrangerea default din tabela Premii'
END
GO

CREATE PROCEDURE p3 AS
BEGIN
	CREATE TABLE CursuriUrmate(
	idCursuriUrmate INT PRIMARY KEY IDENTITY,
	NumeCurs varchar(50),
	idAntrenor INT
	);
	print 'Am adaugat tabela CursuriUrmate'
END
GO

CREATE PROCEDURE p33 AS
BEGIN
	DROP TABLE CursuriUrmate;
	print 'Am sters tabelul CursuriUrmate'
END
GO

CREATE PROCEDURE p4 AS
BEGIN
	ALTER TABLE CursuriUrmate
	ADD DataAbsolvirii DATE;
	print 'Am adaugat campul DataAbsolvirii in tabela CursuriUrmate'
END
GO

CREATE PROCEDURE p44 AS
BEGIN
	ALTER TABLE CursuriUrmate
	DROP COLUMN DataAbsolvirii;
	print 'Am sters campul DataAbsolvirii din tabela CursuriUrmate'
END
GO

CREATE PROCEDURE p5 AS
BEGIN
	ALTER TABLE CursuriUrmate
	ADD CONSTRAINT fk_Andrenor FOREIGN KEY (idAntrenor) references Antrenori(idAntrenor)
	print 'Am adaugat constrangere FK in CursuriUrmate'
END
GO

CREATE PROCEDURE p55 AS
BEGIN
	ALTER TABLE CursuriUrmate
	DROP CONSTRAINT fk_Andrenor;
	print 'Am sters constrangerea FK din CursuriUrmate'
END
GO

CREATE PROCEDURE main @param INT AS
BEGIN
	
	DECLARE @versiune INT
	SELECT @versiune= VersionNo FROM Version;
	
	IF (@param is null)
		BEGIN
			RAISERROR('Parametrul nu poate fi null!', 16,1);
			RETURN 1;
		END
	
	IF (@param<0 OR @param>5)
		BEGIN
			RAISERROR('Numarul dat este invalid!', 16,1);
			RETURN 1;
		END

	IF (@param=@versiune)
		BEGIN
			RAISERROR('Tabela se afla in versiunea curenta!', 16,1);
			RETURN 2;
		END

	DECLARE @numarProc INT;
	DECLARE @numeP VARCHAR(10);
	IF (@versiune<@param)
	BEGIN
		SET @numarProc= @versiune+1;
		--print @numarProc
		WHILE (@versiune<@param)
			BEGIN
				SET @numeP='p'+CONVERT(varchar, @numarProc);
				--print @numeP
				EXEC @numeP
				SET @numarProc=@numarProc+1;
				SET @versiune=@versiune+1;
			END;
		UPDATE Version
			SET VersionNo=@param;
	END
	ELSE
		BEGIN
		IF (@versiune>@param)
			SET @numarProc= @versiune;
			WHILE (@versiune>@param)
				BEGIN
					SET @numeP='p'+CONVERT(VARCHAR, @numarProc)+CONVERT(VARCHAR, @numarProc);
					EXEC @numeP
					SET @numarProc=@numarProc-1;
					SET @versiune=@versiune-1;
				END
			UPDATE Version
				SET VersionNo=@param;
		END
END
GO

--execut main
SELECT * FROM Version


BEGIN TRY
	EXEC main 5;
END TRY
BEGIN CATCH
	SELECT   
        ERROR_NUMBER() AS ErrorNumber  
        ,ERROR_MESSAGE() AS ErrorMessage;  
END CATCH

PRINT @@VERSION
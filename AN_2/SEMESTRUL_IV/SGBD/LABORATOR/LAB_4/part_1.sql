use BazaSportivaSGBD

--Dirty Reads
SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED; --COMMITED
BEGIN TRAN;
SELECT * FROM Abonamente;
WAITFOR DELAY '00:00:07'
SELECT * FROM Abonamente;
COMMIT TRAN;















--Non-repeatable reads
SET TRANSACTION ISOLATION LEVEL READ COMMITTED --REPEATABLE READ
BEGIN TRAN
SELECT * FROM Abonamente
WAITFOR DELAY '00:00:07'
SELECT * FROM Abonamente
COMMIT TRAN

select @@TRANCOUNT











--Phantom Reads
SET TRANSACTION ISOLATION LEVEL REPEATABLE READ --SERIALIZABLE
BEGIN TRAN
SELECT * FROM Abonamente
WAITFOR DELAY '00:00:07'
SELECT * FROM Abonamente
COMMIT TRAN



delete from Abonamente where IdAbonament=53











--Deadlock
BEGIN TRAN
UPDATE Abonamente SET DataExpirarii='dead1' WHERE DataInitierii='02.01.2023'
-- this transaction has exclusively lock on table Books
WAITFOR DELAY '00:00:09'
UPDATE Departamente SET Nume='dead1' WHERE IdDepartament=1
COMMIT TRAN
GO












create or alter procedure Deadlock1
AS 
BEGIN
BEGIN TRAN
UPDATE Abonamente SET DataExpirarii='dead1' WHERE DataInitierii='02.01.2023'
UPDATE Departamente SET Nume='dead1' WHERE IdDepartament=1
COMMIT TRAN
END

UPDATE Abonamente SET DataExpirarii='dead99' WHERE DataInitierii='02.01.2023'
UPDATE Departamente SET Nume='dead99' WHERE IdDepartament=1

exec Deadlock1
select * from Abonamente
select * from Departamente

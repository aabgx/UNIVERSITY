USE BazaSportivaSGBD
GO

select * from Abonamente

--Dirty Reads
BEGIN TRAN
UPDATE Abonamente SET DataInitierii='DR - modificat' WHERE DataExpirarii='12.04.2022'
WAITFOR DELAY '00:00:05'
ROLLBACK TRAN

UPDATE Abonamente SET DataInitierii='12.03.2022' WHERE DataExpirarii='12.04.2022'















--Non-repeatable reads

--pregatire, trebe sa fie cu NRR1 in baza de adte ca sa vad
INSERT INTO Abonamente(DataInitierii,DataExpirarii) VALUES 
('NRR1','01.02.2023')
select * from Abonamente where DataExpirarii='01.02.2023'
UPDATE Abonamente SET DataInitierii='NRR1' WHERE DataExpirarii='01.02.2023'


BEGIN TRAN
WAITFOR DELAY '00:00:05'
UPDATE Abonamente SET DataInitierii='NRR2' WHERE DataExpirarii='01.02.2023'
COMMIT TRAN



SELECT @@TRANCOUNT














--Phantom Reads
BEGIN TRAN
WAITFOR DELAY '00:00:05'
INSERT INTO Abonamente(DataInitierii,DataExpirarii) VALUES 
('PR','02.02.2023')
COMMIT TRAN
GO













INSERT INTO Abonamente(DataInitierii,DataExpirarii) VALUES ('02.01.2023','02.02.2023')

--Deadlock
SET DEADLOCK_PRIORITY HIGH
BEGIN TRAN
UPDATE Departamente SET Nume='dead2' WHERE IdDepartament=1
WAITFOR DELAY '00:00:05'
UPDATE Abonamente SET DataExpirarii='dead2' WHERE DataInitierii='02.01.2023'
COMMIT TRAN

select * from Abonamente
select * from Departamente
GO











UPDATE Abonamente SET DataExpirarii='dead99' WHERE DataInitierii='02.01.2023'
UPDATE Departamente SET Nume='dead99' WHERE IdDepartament=1
GO


create or alter procedure Deadlock2
AS
BEGIN
BEGIN TRAN
UPDATE Departamente SET Nume='dead2' WHERE IdDepartament=1
UPDATE Abonamente SET DataExpirarii='dead2' WHERE DataInitierii='02.01.2023'
COMMIT TRAN
END

exec Deadlock2
select * from Abonamente
select * from Departamente
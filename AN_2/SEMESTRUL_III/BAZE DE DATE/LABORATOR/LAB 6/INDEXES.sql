USE BazaSportiva
go


--view1
CREATE or ALTER VIEW DepartamenteAD AS
	--SELECT d.Nume FROM Departamente d INNER JOIN AbonamenteDepartamente ad on d.IdDepartament=ad.IdDepartament
	--WHERE d.Nume='A_%'
	SELECT d.Nume FROM Departamente d
go

SELECT * FROM DepartamenteAD ORDER BY Nume 
GO
select * from DepartamenteAD

CREATE NONCLUSTERED INDEX idx_Nume ON Departamente(Nume);
DROP INDEX idx_Nume ON Departamente

IF EXISTS (SELECT name FROM sys.indexes WHERE name = N'N_idx_ADNume')
DROP INDEX N_idx_ADNume ON AbonamenteDepartamente;
GO
CREATE NONCLUSTERED INDEX N_idx_ADNume ON AbonamenteDepartamente(Nume);
GO
-- merge?



--view2
CREATE or ALTER VIEW AbonamentePerioada AS
--SELECT DataInitierii,DataExpirarii FROM Abonamente WHERE DataInitierii>'2010-12-11T07:45:00' 
	--AND DataExpirarii<'2023-03-11T07:45:00'
	SELECT DataInitierii FROM Abonamente
go

SELECT * FROM AbonamentePerioada ORDER BY DataInitierii 
GO


IF EXISTS (SELECT name FROM sys.indexes WHERE name = N'N_idx_Abonamente')
DROP INDEX N_idx_Abonamente ON Abonamente;
GO
-- creez index nonclustered N_idx_Abonamente pe Abonamente(DataInitierii)
CREATE NONCLUSTERED INDEX N_idx_Abonamente ON Abonamente (DataInitierii);
GO
-- merge?
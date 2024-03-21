use BazaSportiva
go
create view view1 as
	select * from Abonamente;

create view view2 as
	select p.nume_produs, p.pret_produs, t.tip
	from Produse p inner join Tipuri_Produse t on p.produs_id = t.ID_tip;


CREATE VIEW view3 as
	SELECT i.ID_ingrediente, COUNT(P.produs_id) AS [nr produse]
	FROM Produse P INNER JOIN Ingrediente_Produse pri ON pri.id_produs=P.produs_id
               INNER JOIN Ingrediente i ON pri.ID_ingrediente=i.ID_ingrediente
	GROUP BY i.ID_ingrediente;



insert into Tables(Name) values
	('Ingrediente'),
	('Produse'),
	('Ingrediente_Produse')
go

insert into Views(Name) values
	('view1'),
	('view2'),
	('view3')
go

create or alter procedure insertIngrediente
 @NoOFRows int
as
DECLARE @n int
DECLARE @t VARCHAR(30)
SELECT TOP 1 @NoOfRows =NoOfRows FROM dbo.TestTables 
SET @n=1 
WHILE @n<@NoOfRows 
BEGIN
SET @t ='Ingrediente'+CONVERT(VARCHAR(5),@n)
INSERT INTO Ingrediente (nume_ingrediente) VALUES (@t)
SET @n=@n+1
end


create or alter procedure insertProduse
 @NoOFRows int
as
DECLARE @n int
DECLARE @t VARCHAR(30)
DECLARE @fk int
SELECT TOP 1 @fk =min(ID_tip) FROM Tipuri_Produse
WHILE @n<@NoOfRows 
BEGIN
INSERT INTO Produse(nume_produs,pret_produs,tip_produs)VALUES (@t,10,@fk)
SET @n=@n+1
END


create or alter procedure insertIngredienteProduse
 @NoOFRows int
as
DECLARE @n int
DECLARE @fk int
DECLARE @fk1 int

SELECT TOP 1 @fk =produs_id FROM Produse 
SELECT TOP 1 @fk1 =ID_ingrediente FROM Ingrediente 

WHILE @n<@NoOfRows 
BEGIN
INSERT INTO Ingrediente_Produse(ID_ingrediente,ID_produs)VALUES (@fk1,@fk)
SET @n=@n+1
end


create or alter procedure deleteIngredienteProduse
 @nr int
as
delete from Ingrediente_Produse 
---where 
---ID_ingrediente in (select top (@nr) ID_ingrediente from Ingrediente_Produse order by ID_ingrediente desc)


create or alter procedure deleteProduse
 @nr int
as
delete from Livrari
delete from Comenzi
delete from Produse
---where
	---produs_id in (select top (@nr) produs_id from Produse order by produs_id desc)


create or alter procedure deleteIngrediente
 @nr int
as
delete from Ingrediente 
---where 
	---ID_ingrediente in (select top (@nr) ID_ingrediente from Ingrediente order by ID_ingrediente desc)


create or alter procedure selectIngredienteProduse
@nr int
as
begin
	select top (@nr) * from Ingrediente_Produse
end

create or alter procedure selectProduse
@nr int
as
begin
	select top (@nr) * from Produse
end

create or alter procedure selectIngrediente
@nr int
as
begin
	select top (@nr) * from Ingrediente
end

insert into Tests(Name) values
	('insertIngrediente'),
	('insertProduse'),
	('insertIngredienteProduse'),
	('selectIngrediente'),
	('selectProduse'),
	('selectIngredienteProduse'),
	('deleteIngredienteProduse'),
	('deleteProduse'),
	('deleteIngrediente')


insert into TestViews(TestID, ViewID) values
(4, 1),
(5, 2),
(6, 3)


delete from TestTables


insert into TestTables(TestID, TableID, NoOfRows, Position)
values
(1, 3, 100, 1),
(9, 2, 100, 2),
(2, 1, 500, 3),
(8, 1, 500, 4),
(3, 2, 1000, 5),
(7, 3, 1000, 6)
go

set nocount on
go

create or alter procedure runTests
as
begin

	declare @i int = 1
	
	declare @posMax int
	select @posMax = max(Position) from TestTables

	declare @startAll datetime = getdate()
	declare @testId0 int
	select @testId0 = max(TestRunID) from TestRuns

	insert into TestRuns(TestRuns.Description, StartAt, EndAt) values ('FirstTest', @startAll, @startAll)


	while @i <= @posMax
	begin
		

		
		declare @testId int
		declare @tableId int
		declare @procName varchar(64)
		declare @noRows int
		declare @commId int

		select @testId = TestID from TestTables where Position = @i 
		select @tableId  = TableID from TestTables where Position = @i
		select @noRows = NoOfRows from TestTables where Position = @i

		select @procName = Tests.Name from Tests where TestID = @testId
		
		declare @startTableTest datetime = getdate()
		exec @procName @noRows
		--print @procName
		--print @noRows


		declare @viewStart datetime = getdate()
		select @commId = ViewID from TestViews where TestID = @tableId - 3
		select @procName = Tests.Name from Tests where TestID = @commId
		exec @procName @noRows
		declare @viewEnd datetime = getdate()
		insert into TestRunViews values(@testId0, @tableId, @viewStart, @viewEnd)		
	


		set @i = @i + 1
		select @testId = TestID from TestTables where Position = @i 
		select @tableId  = TableID from TestTables where Position = @i
		select @noRows = NoOfRows from TestTables where Position = @i

		select @procName = Tests.Name from Tests where TestID = @testId
		exec @procName @noRows

		declare @endTableTest datetime = getdate()

		--print @procName
		--print @noRows

		insert into TestRunTables values(@testId0, @tableId, @startTableTest, @endTableTest)

		set @i = @i + 1
		
	end

	declare @endAll datetime = getdate()
	update TestRuns set EndAt = @endAll where StartAt = @startAll

end
go

exec runTests


select * from Ingrediente_Produse
select * from Produse
select * from Ingrediente


select * from TestRunTables
select * from TestRunViews
select * from TestRuns

/*exec insertIngrediente 100
exec insertProduse 1000
exec insertIngredienteProduse 500
exec deleteIngredienteProduse 500
exec deleteProduse 1000
exec deleteIngrediente 100*/
USE BazaSportiva
GO

DELETE FROM Views WHERE 

create view view1 as
	select * from Promotii;

create view view2 as
	select a.DataInitierii, a.DataExpirarii
	from Abonamente a inner join Promotii p on a.IdPromotie = p.IdPromotie;

CREATE VIEW view3 as
	SELECT d.IdDepartament, COUNT(a.IdAbonament) AS [numar]
	FROM Departamente d INNER JOIN AbonamenteDepartamente ad ON ad.IdDepartament=d.IdDepartament
               INNER JOIN Abonamente a ON ad.IdAbonament=a.IdAbonament
	GROUP BY d.IdDepartament;

insert into Tables(Name) values
	('Promotii'),
	('Abonamente'),
	('AbonamenteDepartamente')
go

insert into Views(Name) values
	('view1'),
	('view2'),
	('view3')
go

select * from Views
select * from Tables


create or alter procedure insertPromotii
 @NoOFRows int
as
DECLARE @n int
DECLARE @t VARCHAR(30)
SELECT TOP 1 @NoOfRows =NoOfRows FROM dbo.TestTables 
select top 1 @n =IdPromotie From Promotii order by IdPromotie desc
SET @n=@n+1 
WHILE @n<@NoOfRows 
BEGIN
SET @t ='Promotii'+CONVERT(VARCHAR(5),@n)
INSERT INTO Promotii(IdPromotie,TipPromotie) VALUES (@n, @t)
SET @n=@n+1
end


create or alter procedure insertAbonamente
 @NoOFRows int
as
DECLARE @n int
DECLARE @t VARCHAR(30)
DECLARE @fk int
SELECT TOP 1 @fk =min(IdPromotie) FROM Promotii
select top 1 @n =IdAbonament From Abonamente order by IdAbonament desc
set @n=@n+1
WHILE @n<@NoOfRows 
BEGIN
INSERT INTO Abonamente(IdAbonament,DataInitierii,DataExpirarii,IdPromotie)VALUES (@n,@t,@t,@fk)
SET @n=@n+1
END


create or alter procedure insertAbonamenteDepartamente
 @NoOFRows int
as
DECLARE @n int
DECLARE @fk int
DECLARE @fk1 int

SELECT TOP 1 @fk = IdAbonament FROM Abonamente 
SELECT TOP 1 @fk1 =IdDepartament FROM Departamente 

WHILE @n<@NoOfRows 
BEGIN
INSERT INTO AbonamenteDepartamente(IdAbonament,IdDepartament)VALUES (@fk,@fk1)
SET @n=@n+1
end







create or alter procedure deleteAbonamenteDepartamente
 @nr int
as
delete from AbonamenteDepartamente


create or alter procedure deleteAbonamente
 @nr int
as
delete from Abonamente


create or alter procedure deletePromotii
 @nr int
as
delete from Promotii 


create or alter procedure selectAbonamenteDepartamente
@nr int
as
begin
	select top (@nr) * from AbonamenteDepartamente
end

create or alter procedure selectAbonamente
@nr int
as
begin
	select top (@nr) * from Abonamente
end

create or alter procedure selectPromotii
@nr int
as
begin
	select top (@nr) * from Promotii
end

insert into Tests(Name) values
	('insertPromotii'),
	('insertAbonamente'),
	('insertAbonamenteDepartamente'),
	('selectPromotii'),
	('selectAbonamente'),
	('selectAbonamenteDepartamente'),
	('deleteAbonamenteDepartamente'),
	('deleteAbonamente'),
	('deletePromotii')


insert into TestViews(TestID, ViewID) values
(4, 1),
(5, 2),
(6, 3)


delete from TestTables

--position e nr testului
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
	

	--mergem pana la max position
	declare @posMax int
	select @posMax = max(Position) from TestTables

	--timp de start all tests
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
		
		--moment start punctual pt fiecare
		declare @startTableTest datetime = getdate()
		exec @procName @noRows


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

		insert into TestRunTables values(@testId0, @tableId, @startTableTest, @endTableTest)

		set @i = @i + 1
		
	end

	declare @endAll datetime = getdate()
	update TestRuns set EndAt = @endAll where StartAt = @startAll

end
go

SET IDENTITY_INSERT Promotii ON

exec runTests

select * from TestRunTables
select * from TestRunViews
select * from TestRuns


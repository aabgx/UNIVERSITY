use SalaDeSport
go
create view view1 as
	select * from Promotii;

create view view2 as
	select c.nume, c.tipClient, c.gen
	from Clienti c inner join Promotii p on c.PromID = p.PromID;


CREATE VIEW view3 as
	SELECT p.PrID, COUNT(c.CID) AS [nr clienti]
	FROM Produse p INNER JOIN ClientProduse cp ON cp.PrID=p.PrID
               INNER JOIN Clienti c ON cp.CID=c.CID
	GROUP BY p.PrID;



insert into Tables(Name) values
	('Promotii'),
	('Clienti'),
	('ClientProduse')
go

insert into Views(Name) values
	('view1'),
	('view2'),
	('view3')
go



create or alter procedure insertPromotii
 @NoOFRows int
as
DECLARE @n int
DECLARE @t VARCHAR(30)
SELECT TOP 1 @NoOfRows =NoOfRows FROM dbo.TestTables 
select top 1 @n =PromID From Promotii order by PromID desc
SET @n=@n+1 
WHILE @n<@NoOfRows 
BEGIN
SET @t ='Promotii'+CONVERT(VARCHAR(5),@n)
INSERT INTO Promotii(PromID,TipProm) VALUES (@n, @t)
SET @n=@n+1
end


create or alter procedure insertClienti
 @NoOFRows int
as
DECLARE @n int
DECLARE @t VARCHAR(30)
DECLARE @fk int
SELECT TOP 1 @fk =min(PromID) FROM Promotii
select top 1 @n =CID From Clienti order by CID desc
set @n=@n+1
WHILE @n<@NoOfRows 
BEGIN
INSERT INTO Clienti(CID,TipClient,Gen,Nume,PromID)VALUES (@n,@t,@t,@t,@fk)
SET @n=@n+1
END


create or alter procedure insertClientProduse
 @NoOFRows int
as
DECLARE @n int
DECLARE @fk int
DECLARE @fk1 int

SELECT TOP 1 @fk =CID FROM Clienti 
SELECT TOP 1 @fk1 =PrID FROM Produse 

WHILE @n<@NoOfRows 
BEGIN
INSERT INTO ClientProduse(CID,PrID)VALUES (@fk,@fk1)
SET @n=@n+1
end


create or alter procedure deleteClientProduse
 @nr int
as
delete from ClientProduse 



create or alter procedure deleteClienti
 @nr int
as
delete from Clienti



create or alter procedure deletePromotii
 @nr int
as
delete from Promotii 


create or alter procedure selectClientProduse
@nr int
as
begin
	select top (@nr) * from ClientProduse
end

create or alter procedure selectClient
@nr int
as
begin
	select top (@nr) * from Clienti
end

create or alter procedure selectPromotii
@nr int
as
begin
	select top (@nr) * from Promotii
end

insert into Tests(Name) values
	('insertPromotii'),
	('insertClienti'),
	('insertClientProduse'),
	('selectPromotii'),
	('selectClienti'),
	('selectClientProduse'),
	('deleteClientProduse'),
	('deleteClienti'),
	('deletePromotii')


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

exec runTests

select * from TestRunTables
select * from TestRunViews
select * from TestRuns

/*exec insertPromotii 100
exec insertClienti 1000
exec insertClientProduse 500
exec deleteClientProduse 500
exec deleteClienti 1000
exec deletePromotii 100*/
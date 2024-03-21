USE S7


--CERINTA 3
select Denumire from Inghetate order by Denumire
create index index1 on Inghetate(Denumire)
drop index Inghetate.index1
--aici am modificat slightly baza de date
use BazaSportiva

CREATE TABLE Promotii
(IdPromotie INT PRIMARY KEY IDENTITY,TipPromotie varchar(50));

ALTER TABLE Abonamente
ADD IdPromotie INT

ALTER TABLE Abonamente
ADD CONSTRAINT IdPromotie FOREIGN KEY (IdPromotie) references Promotii(IdPromotie);

DROP TABLE Abonati

use BazaSportiva

INSERT INTO Promotii(TipPromotie)
VALUES ('discount20'),
('discount15'),
('discount10'),
('discount25'),
('Craciun'),
('BlackFriday')

select * from Promotii

SELECT * FROM Abonamente

UPDATE Abonamente SET IdPromotie = 6 WHERE IdAbonament=6;  
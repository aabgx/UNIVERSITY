Create database BazaSportiva
go
use BazaSportiva
go

CREATE TABLE Adrese
(IdAdresa INT PRIMARY KEY IDENTITY, Tara varchar(50),Judet varchar(50),Oras varchar(50),
Strada varchar(50), Numar int);

CREATE TABLE Departamente
(IdDepartament INT PRIMARY KEY IDENTITY, Nume varchar(50));

CREATE TABLE Abonamente
(IdAbonament INT PRIMARY KEY IDENTITY,DataInitierii varchar(50),DataExpirarii varchar(50));

CREATE TABLE AbonamenteDepartamente
(IdAbonament INT FOREIGN KEY REFERENCES Abonamente(IdAbonament), 
IdDepartament INT FOREIGN KEY REFERENCES Departamente(IdAbonament),
CONSTRAINT Id_ad PRIMARY KEY (IdAbonament, IdDepartament));

CREATE TABLE Abonati
(IdAbonat INT PRIMARY KEY IDENTITY, 
Nume varchar(50),Varsta int,NrTelefon varchar(11),AdresaEmail varchar(20),
IdAbonament INT FOREIGN KEY REFERENCES Abonamente(IdAbonament));

CREATE TABLE Plati
(IdPlata INT PRIMARY KEY IDENTITY,
Suma int,DataEfectuarii varchar(20),
IdAbonat INT FOREIGN KEY REFERENCES Abonati(IdAbonat));

CREATE TABLE Antrenori
(IdAntrenor INT PRIMARY KEY IDENTITY,
Nume varchar(40), Varsta int, NrTelefon varchar(11),
AdresaEmail varchar(30), AniExperienta int,
IdDepartament INT FOREIGN KEY REFERENCES Departamente(IdDepartament));

CREATE TABLE Concursuri
(IdConcurs INT PRIMARY KEY IDENTITY,
Data varchar(20), NrParticipanti int,
IdDepartament INT FOREIGN KEY REFERENCES Departamente(IdDepartament)
);

CREATE TABLE Participanti
(IdParticipant INT PRIMARY KEY IDENTITY,
Nume varchar(50), Varsta int, NrTelefon varchar(11),AdresaEmail varchar(50),
IdConcurs INT FOREIGN KEY REFERENCES Concursuri(IdConcurs),
IdAdresa INT FOREIGN KEY REFERENCES Adrese(IdAdresa));

CREATE TABLE Premii
(IdPremiu INT PRIMARY KEY IDENTITY,
Valoare int,
IdConcurs INT FOREIGN KEY REFERENCES Concursuri(IdConcurs));

-- DROP DATABASE BazaSportiva

create schema Marketplace_System;
use Marketplace_System;

CREATE TABLE Login_Signup
(
  Username varchar(32) NOT NULL,
  Password varchar(32),
  PRIMARY KEY (Username)
);

CREATE TABLE Account
(
  Username varchar(32) NOT NULL,
  Type varchar(32),
  Current_balance float,
  PRIMARY KEY (Username),
  FOREIGN KEY (Username) REFERENCES Login_Signup(Username)
);

CREATE TABLE Items
(
  Name varchar(32) NOT NULL,
  Category varchar(32),
  Price float,
  Discount float,
  Stock INT,
  PRIMARY KEY (Name)
);

CREATE TABLE Orderspecs
(
  OID INT NOT NULL AUTO_INCREMENT,
  Client_name varchar(32),
  Totalprice float,
  date_time timestamp
  PRIMARY KEY (OID),
  FOREIGN KEY (Client_name) REFERENCES Account(Username)
);

CREATE TABLE Orderitems
(
  OID INT NOT NULL,
  Item_name varchar(32) NOT NULL,
  Amount float,
  PRIMARY KEY (OID,Item_name),
  FOREIGN KEY (OID) REFERENCES Orderspecs(OID),
  FOREIGN KEY (Item_name) REFERENCES Items(Name)
);

CREATE TABLE Cart
(
  Username varchar(32) NOT NULL,
  Item_name varchar(32) NOT NULL,
  Amount float,
  PRIMARY KEY (Username,Item_name),
  FOREIGN KEY (Username) REFERENCES Account(Username),
  FOREIGN KEY (Item_name) REFERENCES Items(Name)
);

CREATE TABLE Market
(
Cash float,
PRIMARY KEY (Cash)
);


Alter Table login_signup Add mail varchar(32);
Alter Table login_signup Add birthday date;
Alter Table login_signup Add telephone INT;
Insert into market values(0);

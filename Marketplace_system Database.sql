create schema Marketplace_System;
use Marketplace_System;

CREATE TABLE Login_Signup
(
  Username varchar(32) NOT NULL,
  Password INT,
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

Alter Table login_signup Add mail varchar(32);
Alter Table login_signup Add birthday date;
Alter Table login_signup Add telephone INT;

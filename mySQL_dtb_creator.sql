CREATE TABLE BankCompany (
                name VARCHAR(30) NOT NULL,
                PRIMARY KEY (name)
);


CREATE TABLE ClientAccount (
                email VARCHAR(100) NOT NULL,
                name VARCHAR(50) NOT NULL,
                password VARCHAR(50) NOT NULL,
                PRIMARY KEY (email)
);


CREATE UNIQUE INDEX clientaccount_idx
 ON ClientAccount
 ( email, password );

CREATE TABLE Bank_Account (
                IBAN VARCHAR(50) NOT NULL,
                name VARCHAR(100) NOT NULL,
                bankCompany VARCHAR(30) NOT NULL,
                moneyAmount INT NOT NULL,
                email VARCHAR(100) NOT NULL,
                PRIMARY KEY (IBAN)
);


CREATE TABLE Historic_Transfer (
                transfer_id INT AUTO_INCREMENT NOT NULL,
                sendingOrReceiving BOOLEAN NOT NULL,
                description VARCHAR(255),
                connectionEmail VARCHAR(100) NOT NULL,
                amount INT NOT NULL,
                Email VARCHAR(100) NOT NULL,
                PRIMARY KEY (transfer_id)
);


CREATE TABLE Connections (
                connection_id INT AUTO_INCREMENT NOT NULL,
                accountEmail VARCHAR(100) NOT NULL,
                relationEmail VARCHAR(100) NOT NULL,
                PRIMARY KEY (connection_id)
);


insert into ClientAccount(email,name,password) values('j@i.com','John','test');
insert into ClientAccount(email,name,password) values('u@i.com','Jack','test');
insert into ClientAccount(email,name,password) values('o@i.com','Jin','test');
insert into ClientAccount(email,name,password) values('a@i.com','Sarah','test');
insert into ClientAccount(email,name,password) values('d@i.com','Jeff','test');
commit;


ALTER TABLE Bank_Account ADD CONSTRAINT bankcompany_bank_account_fk
FOREIGN KEY (bankCompany)
REFERENCES BankCompany (name)
ON DELETE NO ACTION
ON UPDATE NO ACTION;

ALTER TABLE Connections ADD CONSTRAINT clientaccount_connections_fk
FOREIGN KEY (accountEmail)
REFERENCES ClientAccount (email)
ON DELETE NO ACTION
ON UPDATE NO ACTION;

ALTER TABLE Historic_Transfer ADD CONSTRAINT clientaccount_historic_transfer_fk
FOREIGN KEY (Email)
REFERENCES ClientAccount (email)
ON DELETE NO ACTION
ON UPDATE NO ACTION;

ALTER TABLE Bank_Account ADD CONSTRAINT clientaccount_bank_account_fk
FOREIGN KEY (email)
REFERENCES ClientAccount (email)
ON DELETE NO ACTION
ON UPDATE NO ACTION;

ALTER TABLE Connections ADD CONSTRAINT clientaccount_connections_fk1
FOREIGN KEY (relationEmail)
REFERENCES ClientAccount (email)
ON DELETE NO ACTION
ON UPDATE NO ACTION;

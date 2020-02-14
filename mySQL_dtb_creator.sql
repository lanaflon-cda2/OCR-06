
CREATE TABLE BankCompany (
                name VARCHAR(30) NOT NULL,
                PRIMARY KEY (name)
);


CREATE TABLE UserAccount (
                email VARCHAR(100) NOT NULL,
                name VARCHAR(50) NOT NULL,
                isConnected BOOLEAN DEFAULT false NOT NULL,
                password VARCHAR(50) NOT NULL,
                moneyAmount DECIMAL(6,2) NOT NULL,
                PRIMARY KEY (email)
);


CREATE TABLE Bank_Account (
                iban VARCHAR(50) NOT NULL,
                name VARCHAR(100) NOT NULL,
                bankCompany VARCHAR(30) NOT NULL,
                email VARCHAR(100) NOT NULL,
                PRIMARY KEY (iban)
);


CREATE TABLE Connections (
                connection_id INT AUTO_INCREMENT NOT NULL,
                accountEmail VARCHAR(100) NOT NULL,
                relationEmail VARCHAR(100) NOT NULL,
                PRIMARY KEY (connection_id)
);


CREATE TABLE Transaction (
                transaction_id INT AUTO_INCREMENT NOT NULL,
                sendingOrReceiving BOOLEAN NOT NULL,
                description VARCHAR(255),
                amount DECIMAL(6,2) NOT NULL,
                email VARCHAR(100) NOT NULL,
                connection_id INT NOT NULL,
                PRIMARY KEY (transaction_id)
);
insert into UserAccount(email,name,password,isConnected,moneyAmount) values('j@i.com','John','schaffer',false,200.50);
insert into UserAccount(email,name,password,isConnected,moneyAmount) values('j@a.com','Jeff','loomis',false, 400.00);
insert into UserAccount(email,name,password,isConnected,moneyAmount) values('j@o.com','Sarah','claudius',false, 0.0);
insert into UserAccount(email,name,password,isConnected,moneyAmount) values('j@u.com','Michael','amott',false, 0.0);
commit;

ALTER TABLE Bank_Account ADD CONSTRAINT bankcompany_bank_account_fk
FOREIGN KEY (bankCompany)
REFERENCES BankCompany (name)
ON DELETE NO ACTION
ON UPDATE NO ACTION;

ALTER TABLE Connections ADD CONSTRAINT clientaccount_connections_fk
FOREIGN KEY (accountEmail)
REFERENCES UserAccount (email)
ON DELETE NO ACTION
ON UPDATE NO ACTION;

ALTER TABLE Transaction ADD CONSTRAINT clientaccount_historic_transfer_fk
FOREIGN KEY (email)
REFERENCES UserAccount (email)
ON DELETE NO ACTION
ON UPDATE NO ACTION;

ALTER TABLE Bank_Account ADD CONSTRAINT clientaccount_bank_account_fk
FOREIGN KEY (email)
REFERENCES UserAccount (email)
ON DELETE NO ACTION
ON UPDATE NO ACTION;

ALTER TABLE Connections ADD CONSTRAINT clientaccount_connections_fk1
FOREIGN KEY (relationEmail)
REFERENCES UserAccount (email)
ON DELETE NO ACTION
ON UPDATE NO ACTION;

ALTER TABLE Transaction ADD CONSTRAINT connections_historic_transfer_fk
FOREIGN KEY (connection_id)
REFERENCES Connections (connection_id)
ON DELETE NO ACTION
ON UPDATE NO ACTION;

package com.paymybuddy.transferapps.constants;

public class DBMysSqlQuery {

    public static final String CHECK_CONNECTION =
            "SELECT  dateLog FROM UserAccount where email = ?;";

    public static final String GET_FRIENDS =
            "SELECT relationEmail FROM Connections where accountEmail = ?;";

    public static final String GET_BANKACCOUNT =
            "SELECT name FROM bank_account where email = ?;";

    public static final String GET_ACCOUNT_INFO =
            "SELECT name,moneyAmount FROM UserAccount where email = ?;";

    public static final String GET_TRANSACTIONS =
            "SELECT * FROM Transaction where email=?;";

    public static final String UPDATE_MONEY =
            "UPDATE UserAccount SET moneyAmount = moneyAmount + ? WHERE email=?";

    public static final String UPDATE_CONNECTION =
            "update UserAccount set dateLog=now() WHERE email=?;";

    public static final String ACTIVATE_CONNECTION =
            "update UserAccount set dateLog=now() WHERE email=? AND password=?;";
    public static final String DESACTIVATE_CONNECTION =
            "update UserAccount set dateLog='00/00/0 00:00:00' WHERE email=?";
    public static final String ADD_EMAIL_CONNECTION =
            "INSERT INTO Connections (accountEmail, relationEmail)" +
                    "SELECT  ?,UserAccount.email FROM UserAccount WHERE UserAccount.email=?;";

    public static final String ADD_TRANSACTION =
            "INSERT INTO Transaction (email, relativeEmail, amount, perceiveAmountForApp, description, sendingOrReceiving, date) VALUES (?,?,?,?,?,?,?);";

    public static final String ADD_BANK_ACCOUNT =
            "INSERT INTO Bank_Account (name, email, iban)" +
                    "VALUES (?,?,?);";
}
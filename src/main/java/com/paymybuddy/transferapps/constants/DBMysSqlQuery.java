package com.paymybuddy.transferapps.constants;

public class DBMysSqlQuery {

    public static final String UPDATE_CONNECTION =
            "update UserAccount set isConnected=true WHERE email=? AND password=?;";

    public static final String ADD_EMAIL_CONNECTION =
            "INSERT INTO Connections (accountEmail, relationEmail)"+
            "SELECT  ?,UserAccount.email FROM UserAccount WHERE UserAccount.email=?;";

    public static final String GET_ACCOUNT_INFO =
            "SELECT name,moneyAmount FROM UserAccount where email = ?;";

    public static final String GET_TRANSACTIONS =
            "SELECT sendingOrReceiving,description,amount,connection_id.relationEmail " +
                    "FROM Transaction INNER JOIN Transaction ON Connections.id=b.id" +
                    " where email = ?;";
}
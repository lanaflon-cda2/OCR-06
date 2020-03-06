package com.paymybuddy.transferapps.repositories;

import com.paymybuddy.transferapps.config.DataBaseConfig;
import com.paymybuddy.transferapps.constants.DBMysSqlQuery;
import com.paymybuddy.transferapps.domain.Transaction;
import com.paymybuddy.transferapps.domain.UserAccount;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;


@Repository
public class AccountDAO_old_Version {

    private Logger logger = LogManager.getLogger("AccountDAO");
    public DataBaseConfig dataBaseConfig = new DataBaseConfig();
    @Value("${databaseName}")
    public String databaseName;

    public Boolean isGoodLogs(String log, String password) {
        Connection con = null;
        PreparedStatement ps = null;
        int rs = 0;
        Boolean isconnected = false;
        try {
            con = dataBaseConfig.getConnection(databaseName);
            ps = con.prepareStatement(DBMysSqlQuery.ACTIVATE_CONNECTION);
            ps.setString(1, log);
            ps.setString(2, password);
            rs = ps.executeUpdate();
            if (rs > 0) {
                isconnected = true;
            } else {
                logger.error("Wrong logging or password, please retry");
            }
        } catch (Exception ex) {
            logger.error("Error fetching next available slot", ex);
        } finally {
            closeAll(ps, con, null);
        }
        return isconnected;
    }

    public void disconnect(String email) {
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = dataBaseConfig.getConnection(databaseName);
            ps = con.prepareStatement(DBMysSqlQuery.DESACTIVATE_CONNECTION);
            ps.setString(1, email);
            ps.executeUpdate();
        } catch (Exception ex) {
            logger.error("Error while trying to disconnect", ex);
        } finally {
            closeAll(ps, con, null);
        }
    }

    public void tradingWithBank(String email, String bankAccountName, Double amount) {
        //TODO: make contact with the bank to have permission to withdraw
        Connection con = null;
        PreparedStatement ps = null;
        int rs;
        Boolean isconnected = false;
        try {
            con = dataBaseConfig.getConnection(databaseName);
            ps = con.prepareStatement(DBMysSqlQuery.UPDATE_MONEY);
            ps.setDouble(1, amount);
            ps.setString(2, email);
            rs = ps.executeUpdate();
            if (rs > 0) {
                isconnected = true;
            } else {
                System.out.println("You have reach the max amount of money you can deposit on your account");
            }
        } catch (Exception ex) {
            logger.error("Error fetching next available slot", ex);
        } finally {
            closeAll(ps, con, null);
        }
    }

    public void updateMoneyOnAccount(String email, Double amount) {
        Connection con = null;
        PreparedStatement ps = null;
        int rs;
        Boolean isconnected = false;
        try {
            con = dataBaseConfig.getConnection(databaseName);
            ps = con.prepareStatement(DBMysSqlQuery.UPDATE_MONEY);
            ps.setDouble(1, amount);
            ps.setString(2, email);
            rs = ps.executeUpdate();
            if (rs > 0) {
                isconnected = true;
            } else {
                System.out.println("You have reach the max amount of money you can deposit on your account");
            }
        } catch (Exception ex) {
            logger.error("Error fetching next available slot", ex);
        } finally {
            closeAll(ps, con, null);
        }
    }

    public Boolean addRelativeConnection(String userEmail, String relativeEmail) {
        Connection con = null;
        PreparedStatement ps = null;
        int rs;
        Boolean isconnected = false;
        try {
            con = dataBaseConfig.getConnection(databaseName);
            ps = con.prepareStatement(DBMysSqlQuery.ADD_EMAIL_CONNECTION);
            ps.setString(1, userEmail);
            ps.setString(2, relativeEmail);
            rs = ps.executeUpdate();
            if (rs > 0) {
                isconnected = true;
            } else {
                System.out.println("Sorry, the email doesn't exist in our list of users");
            }
        } catch (Exception ex) {
            logger.error("Error fetching next available slot", ex);
        } finally {
            closeAll(ps, con, null);
        }
        return isconnected;
    }

    public boolean addBankAccount(String email, String bankName, String iban) {
        Connection con = null;
        PreparedStatement ps = null;
        int rs;
        Boolean isconnected = false;
        try {
            con = dataBaseConfig.getConnection(databaseName);
            ps = con.prepareStatement(DBMysSqlQuery.ADD_BANK_ACCOUNT);
            ps.setString(1, bankName);
            ps.setString(2, email);
            ps.setString(3, iban);
            rs = ps.executeUpdate();
            if (rs > 0) {
                System.out.println(bankName + " successfully added.");
                isconnected = true;
            } else {
                System.out.println("Sorry, the IBAN Allready exist, please enter a new one");
            }
        } catch (Exception ex) {
            logger.error("Error fetching next available slot", ex);
        } finally {
            closeAll(ps, con, null);
        }
        return isconnected;
    }

    public void addTransaction(String userEmail, String relativeEmail, Double amount, Double taxapps, String comments) {
        Connection con = null;
        PreparedStatement ps = null;
        int rs;
        try {
            con = dataBaseConfig.getConnection(databaseName);
            ps = con.prepareStatement(DBMysSqlQuery.ADD_TRANSACTION);
            ps.setString(1, userEmail);
            ps.setString(2, relativeEmail);
            ps.setDouble(3, amount);
            ps.setDouble(4, taxapps);
            ps.setString(5, comments);
            ps.setBoolean(6, true);
            ps.setTimestamp(7, Timestamp.from(Instant.now()));
            rs = ps.executeUpdate();
            if (rs > 0) {
                ps.setString(1, relativeEmail);
                ps.setString(2, userEmail);
                ps.setDouble(3, amount * 0.95);
                ps.setDouble(4, taxapps);
                ps.setString(5, comments);
                ps.setBoolean(6, false);
                ps.setTimestamp(7, Timestamp.from(Instant.now()));
                rs = ps.executeUpdate();
                if (rs > 0) {
                    System.out.println("Transaction added");
                } else {
                    System.out.println("Error during the writing of the transaction");
                }
            } else {
                System.out.println("Error during the writing of the transaction");
            }
        } catch (Exception ex) {
            logger.error("Error during database connection", ex);
        } finally {
            closeAll(ps, con, null);
        }
    }

    public Boolean isConnected(String email) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs;
        int rs2;
        Timestamp timestamp = null;
        try {
            con = dataBaseConfig.getConnection(databaseName);
            ps = con.prepareStatement(DBMysSqlQuery.CHECK_CONNECTION);
            ps.setString(1, email);
            rs = ps.executeQuery();
            while (rs.next()) {
                timestamp = rs.getTimestamp(1);
                if (timestamp.after(new Timestamp(System.currentTimeMillis() - 1000 * 60 * 4))) {
                    ps = con.prepareStatement(DBMysSqlQuery.UPDATE_CONNECTION);
                    ps.setString(1, email);
                    rs2 = ps.executeUpdate();
                    if (rs2 > 0) {
                        return true;
                    }
                }
                ;
            }
        } catch (Exception ex) {
            logger.error("Error connecting with the database", ex);
        } finally {
            closeAll(ps, con, null);
        }
        return false;
    }

    public UserAccount getUserInfo(String userEmail) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        UserAccount userAccount = null;
        try {
            con = dataBaseConfig.getConnection(databaseName);
            ps = con.prepareStatement(DBMysSqlQuery.GET_ACCOUNT_INFO);
            ps.setString(1, userEmail);
            rs = ps.executeQuery();
            while (rs.next()) {
                userAccount = new UserAccount(
                        userEmail,
                        rs.getString("name"),
                        rs.getDouble("moneyAmount"));
            }
            dataBaseConfig.closeResultSet(rs);
        } catch (Exception ex) {
            logger.error("Error during getting account info in the Database", ex);
        } finally {
            closeAll(ps, con, rs);
        }
        return userAccount;
    }

    public List<String> getBankAccounts(String userEmail) {
        List<String> bankAccounts = new ArrayList<>();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = dataBaseConfig.getConnection(databaseName);
            ps = con.prepareStatement(DBMysSqlQuery.GET_BANKACCOUNT);
            ps.setString(1, userEmail);
            rs = ps.executeQuery();
            while (rs.next()) {
                bankAccounts.add(rs.getString("name"));
            }
            dataBaseConfig.closeResultSet(rs);
        } catch (Exception ex) {
            logger.error("Error ", ex);
        } finally {
            closeAll(ps, con, rs);
        }
        return bankAccounts;
    }

    public List<String> getRelatives(String userEmail) {
        List<String> friendsName = new ArrayList<>();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = dataBaseConfig.getConnection(databaseName);
            ps = con.prepareStatement(DBMysSqlQuery.GET_FRIENDS);
            ps.setString(1, userEmail);
            rs = ps.executeQuery();
            while (rs.next()) {
                friendsName.add(rs.getString("relationEmail"));
            }
            dataBaseConfig.closeResultSet(rs);
        } catch (Exception ex) {
            logger.error("Error ", ex);
        } finally {
            closeAll(ps, con, rs);
        }
        return friendsName;
    }

    public List<Transaction> getTransaction(String email) {
        List<Transaction> transactions = new ArrayList<>();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = dataBaseConfig.getConnection(databaseName);
            ps = con.prepareStatement(DBMysSqlQuery.GET_TRANSACTIONS);
            ps.setString(1, email);
            rs = ps.executeQuery();

            if (rs.next()) {
                while (rs.next()) {
                    Transaction transaction = new Transaction();
                    if (rs.getBoolean("sendingOrReceiving")) {
                        transaction.setIsCredit("Debit");
                    } else transaction.setIsCredit("Credit");
                    transaction.setMoneyAmount(rs.getDouble("amount"));
                    transaction.setTaxAmount(rs.getDouble("perceiveAmountForApp"));
                    transaction.setRelative(rs.getString("relativeEmail"));
                    transaction.setDescription(rs.getString("description"));
                    transaction.setDate(rs.getTimestamp("date"));
                    transactions.add(transaction);
                }
            } else {
                System.out.println("No transaction recorded yet.");
            }
            dataBaseConfig.closeResultSet(rs);
        } catch (Exception ex) {
            logger.error("Error fetching next available slot", ex);
        } finally {
            closeAll(ps, con, rs);
        }
        return transactions;
    }


    public void closeAll(PreparedStatement ps, Connection con, ResultSet rs) {
        dataBaseConfig.closePreparedStatement(ps);
        try {
            if (ps != null) ps.close();
        } catch (Exception e) {
            logger.error("Error closing PreparedStatement", e);
        }

        try {
            if (con != null) con.close();
        } catch (Exception e) {
            logger.error("Error closing connection", e);
        }
        try {
            if (rs != null) rs.close();
        } catch (Exception e) {
            logger.error("Error closing resulSet", e);
        }
    }

    public void setLogger(Logger testlogger) {
        this.logger = testlogger;
    }
}

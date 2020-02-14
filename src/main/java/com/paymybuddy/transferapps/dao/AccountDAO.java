package com.paymybuddy.transferapps.dao;

import com.paymybuddy.transferapps.config.DataBaseConfig;
import com.paymybuddy.transferapps.constants.DBMysSqlQuery;
import com.paymybuddy.transferapps.domain.UserAccount;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


@Repository
public class AccountDAO {

    private Logger logger = LogManager.getLogger("AccountDAO");
    public DataBaseConfig dataBaseConfig = new DataBaseConfig();

    public Boolean userConnected(String log, String password) {
        Connection con = null;
        PreparedStatement ps = null;
        int rs = 0;
        Boolean isconnected = false;
        try {
            con = dataBaseConfig.getConnection();
            ps = con.prepareStatement(DBMysSqlQuery.UPDATE_CONNECTION);
            ps.setString(1, log);
            ps.setString(2, password);
            rs = ps.executeUpdate();
            if (rs > 0) {
                isconnected = true;
            } else {
                System.out.println("Wrong logging or password, please retry");
            }
        } catch (Exception ex) {
            logger.error("Error fetching next available slot", ex);
        } finally {
            closeAll(ps, con, null);
        }
        return isconnected;
    }

    public Boolean addRelativeConnection(String userEmail, String relativeEmail) {
        Connection con = null;
        PreparedStatement ps = null;
        int rs;
        Boolean isconnected = false;
        try {
            con = dataBaseConfig.getConnection();
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
			closeAll(ps, con,null);
        }
        return isconnected;
    }

	public UserAccount getUserInfo(String userEmail) {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		UserAccount userAccount = new UserAccount();
		try {
			con = dataBaseConfig.getConnection();
			ps = con.prepareStatement(DBMysSqlQuery.GET_ACCOUNT_INFO);
			ps.setString(1, userEmail);
			rs = ps.executeQuery();
			userAccount.setEmail(userEmail);
            while (rs.next()) {
                userAccount.setName(rs.getString("name"));
                userAccount.setMoneyAmount(rs.getDouble("moneyAmount"));
            }
			dataBaseConfig.closeResultSet(rs);
		} catch (Exception ex) {
			logger.error("Error during getting account info in the Database", ex);
		} finally {
			closeAll(ps, con, rs);
		}
		return userAccount;
	}

    public String getTransaction(String email) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String result = "";
        try {
            con = dataBaseConfig.getConnection();
            ps = con.prepareStatement(DBMysSqlQuery.GET_TRANSACTIONS);
            ps.setString(1, email);
            rs = ps.executeQuery();

            if (rs.next()) {
                result = rs.getString(1);
            } else {
                System.out.println("Wrong logging or password, please retry");
            }
            dataBaseConfig.closeResultSet(rs);
        } catch (Exception ex) {
            logger.error("Error fetching next available slot", ex);
        } finally {
            closeAll(ps, con, rs);
        }
        return result;
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

package com.paymybuddy.transferapps.dao;

import com.paymybuddy.transferapps.config.DataBaseConfig;
import com.paymybuddy.transferapps.constants.DBConstants;
import com.paymybuddy.transferapps.domain.Transaction;
import com.paymybuddy.transferapps.domain.UserAccount;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class AccountDAO {

	private Logger logger = LogManager.getLogger("ParkingSpotDAO");

	public DataBaseConfig dataBaseConfig = new DataBaseConfig();

	public UserAccount getUserAccount(String log, String password) {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		UserAccount result= new UserAccount();
		try {
			con = dataBaseConfig.getConnection();
			ps = con.prepareStatement(DBConstants.GET_USERACCOUNT);
			ps.setString(1,log);
			ps.setString(1,password);
			rs = ps.executeQuery();

			if (rs.next()) {
				result = (UserAccount) rs.getObject(1);
			}
			dataBaseConfig.closeResultSet(rs);
			dataBaseConfig.closePreparedStatement(ps);
		} catch (Exception ex) {
			logger.error("Error fetching next available slot", ex);
		} finally {
		    try { if (rs != null) rs.close(); } catch (Exception e) {logger.error("Error closing next available slot", e);};
		    try { if (ps != null) ps.close(); } catch (Exception e) {logger.error("Error closing next available slot", e);};
		    try { if (con != null) con.close(); } catch (Exception e) {logger.error("Error closing next available slot", e);};
		}
		return result;
	}

	public boolean updateAccount(Transaction transaction) {
		Connection con = null;
		PreparedStatement ps = null;
		try {
			con = dataBaseConfig.getConnection();
			ps = con.prepareStatement(DBConstants.UPDATE_PARKING_SPOT);
			ps.setDouble(1, transaction.getMoneyAmount());
			ps.setString(2, transaction.getDescription());

			int updateRowCount = ps.executeUpdate();
			dataBaseConfig.closePreparedStatement(ps);
			return (updateRowCount == 1);
		} catch (Exception ex) {
			logger.error("Error updating parking info", ex);
			return false;
		} finally {
		    try { if (ps != null) ps.close(); } catch (Exception e) {logger.error("Error closing updating parking info", e);};
		    try { if (con != null) con.close(); } catch (Exception e) {logger.error("Error closing updating parking info", e);};
		}
	}
	
	public void setLogger(Logger testlogger) {
		this.logger = testlogger;
	}


}

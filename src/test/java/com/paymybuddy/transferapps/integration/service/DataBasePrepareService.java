package com.paymybuddy.transferapps.integration.service;

import com.paymybuddy.transferapps.config.DataBaseConfig;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Connection;

public class DataBasePrepareService {

    @Autowired
    DataBaseConfig dataBaseTestConfig;

    public void clearDataBaseEntries() {
        Connection connection = null;
        try {
            connection = dataBaseTestConfig.getConnection("transferapptest");

            // set parking entries to available
            connection.prepareStatement("update parking set available = true").execute();

            // clear ticket entries;
            connection.prepareStatement("truncate table ticket").execute();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            dataBaseTestConfig.closeConnection(connection);
        }
    }

}

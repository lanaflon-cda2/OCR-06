package com.paymybuddy.transferapps.repositories;

import com.paymybuddy.transferapps.constants.DBMysSqlQuery;
import com.paymybuddy.transferapps.domain.Transaction;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Repository
public class AccountDAO {

    private static final Logger logger = LogManager.getLogger("AccountService");
    EntityManager em;

    public Boolean isGoodLogs(String log, String password) {
        Query query = em.createQuery(DBMysSqlQuery.ACTIVATE_CONNECTION);
        try {
            query
                    .setParameter(1, password)
                    .setParameter(2, log)
                    .executeUpdate();
            return true;
        } catch (Exception ex) {
            logger.error("Error fetching next available slot", ex);
        }
        return false;
    }

    public Boolean isConnected(String email) {
        Query query = em.createQuery(DBMysSqlQuery.CHECK_CONNECTION);
        Timestamp timestamp = null;
        try {
            List<Timestamp> time = query.setParameter(1, email).getResultList();
            if (!time.isEmpty()) {
                timestamp = time.get(0);
                if (timestamp.after(new Timestamp(System.currentTimeMillis() - 1000 * 60 * 4))) {
                    query = em.createQuery(DBMysSqlQuery.UPDATE_CONNECTION);
                    query
                            .setParameter(1, email)
                            .executeUpdate();
                    return true;
                }
            }
            return false;
        } catch (Exception ex) {
            logger.error("An error has occur during database connection", ex);
        }
        return false;
    }

    public void disconnect(String email) {

        Query query = em.createQuery(DBMysSqlQuery.DESACTIVATE_CONNECTION);
        try {
            query
                    .setParameter(1, email)
                    .executeUpdate();
        } catch (Exception ex) {
            logger.error("Error fetching next available slot", ex);
        }
    }

    public void tradingWithBank(String email, String bankAccountName, Double amount) {
        //TODO: make contact with the bank to have permission to withdraw
        Query query = em.createQuery(DBMysSqlQuery.DESACTIVATE_CONNECTION);
        try {
            query
                    .setParameter(1, amount)
                    .setParameter(2, email)
                    .executeUpdate();
        } catch (Exception ex) {
            logger.error("Error, you have reach the maximum amount of money", ex);
        }
    }

    public void updateMoneyOnAccount(String email, Double amount) {
        Query query = em.createQuery(DBMysSqlQuery.UPDATE_MONEY);
        try {
            query
                    .setParameter(1, amount)
                    .setParameter(2, email)
                    .executeUpdate();
        } catch (Exception ex) {
            logger.error("Error, you have reach the maximum amount of money", ex);
        }
    }

    public Boolean addRelativeConnection(String userEmail, String relativeEmail) {
        Query query = em.createQuery(DBMysSqlQuery.ADD_EMAIL_CONNECTION);
        try {
            query
                    .setParameter(1, userEmail)
                    .setParameter(2, relativeEmail)
                    .executeUpdate();
            return true;
        } catch (Exception ex) {
            logger.error("This email doesn't exist in our list", ex);
        }
        return false;
    }

    public boolean addBankAccount(String email, String bankName, String iban) {
        Query query = em.createQuery(DBMysSqlQuery.ADD_BANK_ACCOUNT);
        try {
            query
                    .setParameter(1, bankName)
                    .setParameter(2, email)
                    .setParameter(3, iban)
                    .executeUpdate();
            return true;
        } catch (Exception ex) {
            logger.error("The IBAN Allready exist, please enter a new one", ex);
        }
        return false;
    }

    public void addTransaction(Transaction transaction) {
        em.getTransaction().begin();
        em.persist(transaction);
        em.getTransaction().commit();
    }

}

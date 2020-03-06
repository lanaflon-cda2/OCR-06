package com.paymybuddy.transferapps.service;

import com.paymybuddy.transferapps.constants.DBMysSqlQuery;
import com.paymybuddy.transferapps.domain.BankAccount;
import com.paymybuddy.transferapps.domain.Transaction;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import java.util.List;


public interface TransactionRepository extends Repository<Transaction, Long> {
    @Query(value = DBMysSqlQuery.GET_TRANSACTIONS, nativeQuery = true)
    List<Transaction> findByEmail(String email);
}

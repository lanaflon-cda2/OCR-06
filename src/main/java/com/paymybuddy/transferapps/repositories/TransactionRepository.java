package com.paymybuddy.transferapps.repositories;

import com.paymybuddy.transferapps.constants.DBMysSqlQuery;
import com.paymybuddy.transferapps.domain.BankAccount;
import com.paymybuddy.transferapps.domain.Transaction;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends CrudRepository<Transaction, Long> {
    List<Transaction> findByEmail(String email);
}

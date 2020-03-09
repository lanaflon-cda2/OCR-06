package com.paymybuddy.transferapps.service;

import com.paymybuddy.transferapps.constants.DBMysSqlQuery;
import com.paymybuddy.transferapps.domain.BankAccount;
import com.paymybuddy.transferapps.domain.Password;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.Repository;

import java.util.List;


public interface PasswordRepository extends CrudRepository<Password, Long> {
    Password findFirstByEmail(String email);
}

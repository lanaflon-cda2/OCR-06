package com.paymybuddy.transferapps.service;

import com.paymybuddy.transferapps.constants.DBMysSqlQuery;
import com.paymybuddy.transferapps.domain.UserAccount;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.*;



public interface UserAccountRepository extends CrudRepository<UserAccount, Long> {

    UserAccount findFirstByEmail(String email);
}

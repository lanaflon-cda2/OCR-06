package com.paymybuddy.transferapps.repositories;

import com.paymybuddy.transferapps.domain.UserAccount;
import org.springframework.data.repository.*;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface UserAccountRepository extends CrudRepository<UserAccount, Long> {
    Optional<UserAccount> findByEmail(String email);

}

package com.paymybuddy.transferapps.repositories;

import com.paymybuddy.transferapps.domain.Password;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PasswordRepository extends CrudRepository<Password, Long> {
    Password findFirstByEmail(String email);
}

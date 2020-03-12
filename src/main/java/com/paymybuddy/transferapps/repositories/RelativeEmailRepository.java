package com.paymybuddy.transferapps.repositories;

import com.paymybuddy.transferapps.domain.RelationEmail;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RelativeEmailRepository extends CrudRepository<RelationEmail, Long> {
    List<RelationEmail> findByEmail(String email);
    Optional<RelationEmail> findByEmailAndRelativeEmail(String email, String relativeEmail);
}

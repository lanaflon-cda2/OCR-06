package com.paymybuddy.transferapps.service;

import com.paymybuddy.transferapps.constants.DBMysSqlQuery;
import com.paymybuddy.transferapps.domain.RelationEmail;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface RelativeEmailRepository extends CrudRepository<RelationEmail, Long> {
    @Query(value = DBMysSqlQuery.GET_FRIENDS, nativeQuery = true)
    List<RelationEmail> findByEmail(String email);
}

package com.paymybuddy.transferapps.service;


import com.paymybuddy.transferapps.domain.RelationEmail;
import com.paymybuddy.transferapps.repositories.RelativeEmailRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@Slf4j
public class RelativeService extends MainService {


    @Autowired
    private RelativeEmailRepository relativeEmailRepository;


    public boolean addAFriend(RelationEmail relationEmail) {
        if (!userAccountRepository.findByEmail(relationEmail.getRelativeEmail()).isEmpty()) {
            relationEmail.setEmail(userAccountSession.getEmail());
            Optional<RelationEmail> existingRelation =
                    relativeEmailRepository.findByEmailAndRelativeEmail(
                            relationEmail.getEmail(),
                            relationEmail.getRelativeEmail());
            if (existingRelation.isEmpty()) {
                relativeEmailRepository.save(relationEmail);
                return true;
            } else {
                log.error("The email is already added to your friendlist");
                return false;
            }
        } else {
            log.error("The email is not recorded in the database");
            return false;
        }
    }

    public List<String> getRelatives() {
        List<String> relativeList = new ArrayList<>();
        for (RelationEmail relative : relativeEmailRepository.findByEmail(userAccountSession.getEmail())) {
            relativeList.add(relative.getRelativeEmail());
        }
        return relativeList;
    }
}
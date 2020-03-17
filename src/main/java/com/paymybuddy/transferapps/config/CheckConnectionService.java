package com.paymybuddy.transferapps.config;

import com.paymybuddy.transferapps.domain.UserAccount;
import com.paymybuddy.transferapps.repositories.UserAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CheckConnectionService implements UserDetailsService {

    @Autowired
    UserAccountRepository userAccountRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<UserAccount> user =  userAccountRepository.findByEmail(email);

        user.orElseThrow(() -> new UsernameNotFoundException("Not found: " + email));

        return user.map(MyUserDetails::new).get();
    }
}

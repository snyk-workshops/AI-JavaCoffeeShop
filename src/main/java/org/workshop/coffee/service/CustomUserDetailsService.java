package org.workshop.coffee.service;

import org.workshop.coffee.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    PersonRepository personRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = personRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }

        var role = new SimpleGrantedAuthority(user.getRoles().toString());
        return new User(user.getUsername(), user.getEncryptedPassword(), List.of(role));
    }
}

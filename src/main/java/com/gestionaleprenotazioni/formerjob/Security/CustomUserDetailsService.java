package com.gestionaleprenotazioni.formerjob.Security;

import com.gestionaleprenotazioni.formerjob.Model.User;
import com.gestionaleprenotazioni.formerjob.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        // cerchiamo l'utente nel DB tramite email
        User user = userRepository.findByEmail(email);

            if(user == null)
            {
                throw new UsernameNotFoundException("Utente non trovato: " +email);
            }

        // convertiamo il nostro Role in un GrantedAuthority che Spring Security capisce
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(user.getRole().name());

        // restituiamo un UserDetails con email, password e ruolo
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                List.of(authority)
        );
    }
}
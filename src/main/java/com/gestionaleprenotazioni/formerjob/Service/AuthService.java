package com.gestionaleprenotazioni.formerjob.Service;

import com.gestionaleprenotazioni.formerjob.Dto.LoginRequestDto;
import com.gestionaleprenotazioni.formerjob.Dto.LoginResponseDto;
import com.gestionaleprenotazioni.formerjob.Dto.RegisterRequestDto;
import com.gestionaleprenotazioni.formerjob.Model.Role;
import com.gestionaleprenotazioni.formerjob.Model.User;
import com.gestionaleprenotazioni.formerjob.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AuthService {

    private final UserRepository userRepository;

    // Opzionale: presente solo con profilo "prod" (BCrypt).
    // In "dev" è null → confronto in plain text.
    @Autowired(required = false)
    private PasswordEncoder passwordEncoder;

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public LoginResponseDto login(LoginRequestDto request) {

        // 1. Cerca utente per email
        User user = userRepository.findByEmail(request.getEmail());
        if (user == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Credenziali non valide");
        }

        // 2. Verifica password
        boolean passwordMatch;
        if (passwordEncoder != null) {
            // profilo prod: BCrypt
            passwordMatch = passwordEncoder.matches(request.getPassword(), user.getPassword());
        } else {
            // profilo dev: plain text
            passwordMatch = request.getPassword().equals(user.getPassword());
        }

        if (!passwordMatch) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Credenziali non valide");
        }

        // 3. Restituisce i dati dell'utente (senza password)
        return new LoginResponseDto(
                user.getId(),
                user.getName(),
                user.getSurname(),
                user.getEmail(),
                user.getRole(),
                true
        );
    }

    public LoginResponseDto register(RegisterRequestDto request) {

        // 1. Controlla che l'email non sia già in uso
        if (userRepository.findByEmail(request.getEmail()) != null) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email già in uso");
        }

        // 2. Codifica la password (BCrypt in prod, plain text in dev)
        String encodedPassword = (passwordEncoder != null)
                ? passwordEncoder.encode(request.getPassword())
                : request.getPassword();

        // 3. Crea il nuovo utente
        User user = new User();
        user.setName(request.getName());
        user.setSurname(request.getSurname());
        user.setEmail(request.getEmail());
        user.setPassword(encodedPassword);
        user.setDateOfBirth(request.getDateOfBirth());
        user.setRole(Role.USER); // ruolo di default per nuovi utenti

        // 4. Salva nel database
        User saved = userRepository.save(user);

        // 5. Restituisce i dati (senza password)
        return new LoginResponseDto(
                saved.getId(),
                saved.getName(),
                saved.getSurname(),
                saved.getEmail(),
                saved.getRole(),
                true
        );
    }
}


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

    @Autowired(required = false)
    private PasswordEncoder passwordEncoder;

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public LoginResponseDto register(RegisterRequestDto request) {

        if (userRepository.findByEmail(request.getEmail()) != null) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email già in uso");
        }

        String encodedPassword = (passwordEncoder != null)
                ? passwordEncoder.encode(request.getPassword())
                : request.getPassword();

        User user = new User();
        user.setName(request.getName());
        user.setSurname(request.getSurname());
        user.setEmail(request.getEmail());
        user.setPassword(encodedPassword);
        user.setDateOfBirth(request.getDateOfBirth());
        user.setRole(Role.USER);

        User saved = userRepository.save(user);

        return new LoginResponseDto(
                saved.getId(),
                saved.getName(),
                saved.getSurname(),
                saved.getEmail(),
                saved.getRole(),
                true
        );
    }

    public LoginResponseDto login(LoginRequestDto request) {
        User user = userRepository.findByEmail(request.getEmail());
        
        if (user == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Credenziali non valide");
        }
        
        String inputPassword = request.getPassword();
        String storedPassword = user.getPassword();
        boolean passwordMatches = false;
        
        if (passwordEncoder != null) {
            passwordMatches = passwordEncoder.matches(inputPassword, storedPassword);
        } else {
            passwordMatches = inputPassword.equals(storedPassword);
        }
        
        if (!passwordMatches) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Credenziali non valide");
        }
        
        return new LoginResponseDto(
                user.getId(),
                user.getName(),
                user.getSurname(),
                user.getEmail(),
                user.getRole(),
                true
        );
    }
}

package com.gestionaleprenotazioni.formerjob.Service;

import com.gestionaleprenotazioni.formerjob.Dto.LoginRequestDto;
import com.gestionaleprenotazioni.formerjob.Dto.LoginResponseDto;
import com.gestionaleprenotazioni.formerjob.Dto.RegisterRequestDto;
import com.gestionaleprenotazioni.formerjob.Model.Role;
import com.gestionaleprenotazioni.formerjob.Model.User;
import com.gestionaleprenotazioni.formerjob.Repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

/**
 * Service dedicato all'autenticazione e registrazione utenti.
 *
 * <p>Gestisce:
 * <ul>
 *     <li>Login con verifica credenziali</li>
 *     <li>Registrazione con controllo unicita email</li>
 *     <li>Hash della password tramite {@link PasswordEncoder} quando disponibile</li>
 * </ul>
 * </p>
 */
@Service
public class AuthService {

    /**
     * Repository per accesso ai dati utente.
     */
    private final UserRepository userRepository;

    /**
     * Encoder per hash e verifica password.
     * Puo essere null in configurazioni di test/non standard.
     */
    private final PasswordEncoder passwordEncoder;

    /**
     * Costruttore con dependency injection.
     *
     * @param userRepository repository utenti
     * @param passwordEncoder encoder password
     */
    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Esegue il login di un utente.
     *
     * <p>Passi principali:
     * <ol>
     *     <li>Cerca l'utente tramite email</li>
     *     <li>Verifica la password (encoded o plain, in base alla disponibilita dell'encoder)</li>
     *     <li>Restituisce i dati di sessione/login</li>
     * </ol>
     * </p>
     *
     * @param request payload contenente email e password
     * @return risposta login con dati utente e flag di successo
     * @throws ResponseStatusException UNAUTHORIZED se email non esiste o password non valida
     */
    public LoginResponseDto login(LoginRequestDto request) {
        User user = userRepository.findByEmail(request.getEmail());
        if (user == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Credenziali non valide");
        }

        boolean passwordMatch;
        if (passwordEncoder != null) {
            passwordMatch = passwordEncoder.matches(request.getPassword(), user.getPassword());
        } else {
            passwordMatch = request.getPassword().equals(user.getPassword());
        }

        if (!passwordMatch) {
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

    /**
     * Registra un nuovo utente.
     *
     * <p>Passi principali:
     * <ol>
     *     <li>Controlla che l'email non sia gia usata</li>
     *     <li>Codifica la password se presente un {@link PasswordEncoder}</li>
     *     <li>Crea e salva l'utente con ruolo di default {@link Role#USER}</li>
     *     <li>Restituisce i dati del nuovo utente</li>
     * </ol>
     * </p>
     *
     * @param request payload registrazione
     * @return risposta con dati utente appena registrato e flag di successo
     * @throws ResponseStatusException CONFLICT se email gia in uso
     */
    public LoginResponseDto register(RegisterRequestDto request) {
        if (userRepository.findByEmail(request.getEmail()) != null) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email gia in uso");
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
}

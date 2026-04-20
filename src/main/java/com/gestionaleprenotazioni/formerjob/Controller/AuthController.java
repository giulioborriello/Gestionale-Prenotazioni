package com.gestionaleprenotazioni.formerjob.Controller;

import com.gestionaleprenotazioni.formerjob.Dto.LoginRequestDto;
import com.gestionaleprenotazioni.formerjob.Dto.LoginResponseDto;
import com.gestionaleprenotazioni.formerjob.Dto.RegisterRequestDto;
import com.gestionaleprenotazioni.formerjob.Service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller REST per autenticazione e registrazione utenti.
 *
 * <p>Espone endpoint sotto il path base {@code /auth} e delega la logica
 * applicativa al servizio {@link AuthService}.</p>
 */
@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:4200")
public class AuthController {

    /**
     * Service che contiene la logica di login/register.
     */
    private final AuthService authService;

    /**
     * Costruttore con dependency injection del service.
     *
     * @param authService servizio autenticazione
     */
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    /**
     * Esegue il login utente.
     *
     * <p>Endpoint: {@code POST /auth/login}</p>
     *
     * @param request payload con credenziali (email, password)
     * @return {@link ResponseEntity} con {@link LoginResponseDto} e status 200 (OK)
     */
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto request) {
        LoginResponseDto response = authService.login(request);
        return ResponseEntity.ok(response);
    }

    /**
     * Registra un nuovo utente.
     *
     * <p>Endpoint: {@code POST /auth/register}</p>
     * <p>Esempio body:
     * { "name": "...", "surname": "...", "email": "...", "password": "...", "taxCode": "...", "dateOfBirth": "..." }</p>
     *
     * @param request payload registrazione
     * @return {@link ResponseEntity} con {@link LoginResponseDto} e status 201 (CREATED)
     * @throws org.springframework.web.server.ResponseStatusException 409 (CONFLICT) se email gia in uso
     */
    @PostMapping("/register")
    public ResponseEntity<LoginResponseDto> register(@RequestBody RegisterRequestDto request) {
        LoginResponseDto response = authService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}

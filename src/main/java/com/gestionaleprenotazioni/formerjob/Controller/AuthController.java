package com.gestionaleprenotazioni.formerjob.Controller;

import com.gestionaleprenotazioni.formerjob.Dto.LoginRequestDto;
import com.gestionaleprenotazioni.formerjob.Dto.LoginResponseDto;
import com.gestionaleprenotazioni.formerjob.Dto.RegisterRequestDto;
import com.gestionaleprenotazioni.formerjob.Service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:4200")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto request) {
        LoginResponseDto response = authService.login(request);
        return ResponseEntity.ok(response);
    }

    /**
     * POST /auth/register
     * Body: { "name": "...", "surname": "...", "email": "...", "password": "...", "taxCode": "...", "dateOfBirth": "..." }
     * Risposta 201: utente registrato (senza password)
     * Risposta 409: email già in uso
     */
    @PostMapping("/register")
    public ResponseEntity<LoginResponseDto> register(@RequestBody RegisterRequestDto request) {
        LoginResponseDto response = authService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}


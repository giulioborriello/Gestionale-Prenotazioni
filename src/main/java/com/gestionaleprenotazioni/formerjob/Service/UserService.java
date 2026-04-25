package com.gestionaleprenotazioni.formerjob.Service;

import com.gestionaleprenotazioni.formerjob.Dto.UserDto;
import com.gestionaleprenotazioni.formerjob.Mapper.Mapper;
import com.gestionaleprenotazioni.formerjob.Mapper.UserMapper;
import com.gestionaleprenotazioni.formerjob.Model.User;
import com.gestionaleprenotazioni.formerjob.Repository.UserRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

// Segnala a Spring che questa classe è un componente del Service layer
@Service
public class UserService extends AbstractService<User,UserDto>
{
    // Mapper specifico per User → UserDto (generato da MapStruct)
    // Converte entità JPA in DTO sicuri per l'API
    private final UserMapper userMapper;
    // Repository specifico per operazioni CRUD + query custom su User
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;
    // Costruttore con Dependency Injection
    // Spring inietta automaticamente le dipendenze dichiarate
    public UserService(JpaRepository<User, Integer> repository, Mapper<UserDto,User> converter, UserMapper userMapper, UserRepository userRepository,
                       PasswordEncoder passwordEncoder, EmailService emailService) {
        // Chiama super per inizializzare la classe base AbstractService
        // (eredita metodi CRUD generici: save, findAll, delete, etc.)
        super(repository, converter);
        // Salva le dipendenze specifiche per i metodi custom
        this.userMapper = userMapper;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
    }

    // Cerca utenti per nome esatto (metodo derivato JPA)
    // SQL generato: SELECT * FROM user WHERE name = ?
    public List<UserDto> findByName(String name)
    {
        // Repository → List<User> → Mapper → List<UserDto>
        return userMapper.toDTOList(userRepository.findByName(name));
    }

    // Cerca utenti per cognome esatto
    // SQL generato: SELECT * FROM user WHERE surname = ?
    public List<UserDto> findBySurname(String surname)
    {

        return userMapper.toDTOList(userRepository.findBySurname(surname));
    }

    // Cerca utente per email (restituisce singolo risultato)
    // Solitamente usato per login/recupero password
    public UserDto findByEmail(String email)
    {
        // Single entity → Single DTO
        return userMapper.toDTO(userRepository.findByEmail(email));
    }
    // Cerca per nome E cognome (AND logico)
    // SQL generato: SELECT * FROM user WHERE name = ? AND surname = ?
    public List<UserDto> findByNameAndSurname(String name, String surname)
    {
        return userMapper.toDTOList(userRepository.findByNameAndSurname(name, surname));
    }
    // Cerca per cognome E email (utile per recupero account)
    public UserDto findBySurnameAndEmail(String surname,String email)
    {
        return userMapper.toDTO(userRepository.findBySurnameAndEmail(surname,email));
    }

    public void generateResetToken(String email) {
        User user = userRepository.findByEmail(email);
        if (user == null) throw new IllegalArgumentException("Email non trovata");

        String token = UUID.randomUUID().toString();
        user.setResetToken(token);
        user.setResetTokenExpiry(new Date(System.currentTimeMillis() + 3600000)); // 1 ora
        userRepository.save(user);

        // Manda l'email col link
        String link = "http://localhost:4200/reset-password?token=" + token;
        emailService.sendSimpleEmail(
                user.getEmail(),
                "Reset Password - EventIO",
                "Ciao " + user.getName() + ",\n\nClicca qui per resettare la password:\n" + link + "\n\nIl link scade tra 1 ora.\n\nTeam EventIO"
        );
    }

    public void resetPassword(String token, String newPassword) {
        Optional<User> opt = userRepository.findByResetToken(token);
        if (opt.isEmpty()) throw new IllegalArgumentException("Token non valido");

        User user = opt.get();
        if (user.getResetTokenExpiry().before(new Date()))
            throw new IllegalArgumentException("Token scaduto");

        user.setPassword(passwordEncoder.encode(newPassword));
        user.setResetToken(null);
        user.setResetTokenExpiry(null);
        userRepository.save(user);
    }
}

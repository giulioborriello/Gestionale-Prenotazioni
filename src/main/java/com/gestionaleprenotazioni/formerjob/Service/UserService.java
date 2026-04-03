package com.gestionaleprenotazioni.formerjob.Service;

import com.gestionaleprenotazioni.formerjob.Dto.UserDto;
import com.gestionaleprenotazioni.formerjob.Mapper.Mapper;
import com.gestionaleprenotazioni.formerjob.Mapper.UserMapper;
import com.gestionaleprenotazioni.formerjob.Model.User;
import com.gestionaleprenotazioni.formerjob.Repository.UserRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
// Segnala a Spring che questa classe è un componente del Service layer
@Service
public class UserService extends AbstractService<User,UserDto>
{
    // Mapper specifico per User → UserDto (generato da MapStruct)
    // Converte entità JPA in DTO sicuri per l'API
    private final UserMapper userMapper;
    // Repository specifico per operazioni CRUD + query custom su User
    private final UserRepository userRepository;
    // Costruttore con Dependency Injection
    // Spring inietta automaticamente le dipendenze dichiarate
    public UserService(JpaRepository<User, Integer> repository, Mapper<UserDto,User> converter, UserMapper userMapper, UserRepository userRepository) {
        // Chiama super per inizializzare la classe base AbstractService
        // (eredita metodi CRUD generici: save, findAll, delete, etc.)
        super(repository, converter);
        // Salva le dipendenze specifiche per i metodi custom
        this.userMapper = userMapper;
        this.userRepository = userRepository;
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
}

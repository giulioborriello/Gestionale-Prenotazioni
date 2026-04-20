package com.gestionaleprenotazioni.formerjob.Service;

import com.gestionaleprenotazioni.formerjob.Dto.UserDto;
import com.gestionaleprenotazioni.formerjob.Mapper.Mapper;
import com.gestionaleprenotazioni.formerjob.Mapper.UserMapper;
import com.gestionaleprenotazioni.formerjob.Model.User;
import com.gestionaleprenotazioni.formerjob.Repository.UserRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
/**
 * Servizio per la gestione degli utenti.
 * Estende AbstractService per fornire operazioni di base e metodi personalizzati
 * per trovare utenti basati su nome, cognome, email e combinazioni di essi.
 */
@Service
public class UserService extends AbstractService<User,UserDto>
{
    private final UserMapper userMapper;

    private final UserRepository userRepository;
    /**
     * Costruttore per UserService.
     *
     * @param repository il repository JPA per le entità User
     * @param converter il mapper generico per convertire UserDto a User
     * @param userMapper il mapper specifico per convertire liste di User a UserDto
     * @param userRepository il repository specifico per User
     */
    public UserService(JpaRepository<User, Integer> repository, Mapper<UserDto,User> converter, UserMapper userMapper, UserRepository userRepository) {
        super(repository, converter);
        this.userMapper = userMapper;
        this.userRepository = userRepository;
    }
    /**
     * Trova una lista di utenti per nome.
     *
     * @param name il nome da cercare
     * @return una lista di UserDto corrispondenti al nome specificato
     */
    public List<UserDto> findByName(String name)
    {
        return userMapper.toDTOList(userRepository.findByName(name));
    }
    /**
     * Trova una lista di utenti per cognome.
     *
     * @param surname il cognome da cercare
     * @return una lista di UserDto corrispondenti al cognome specificato
     */
    public List<UserDto> findBySurname(String surname)
    {
        return userMapper.toDTOList(userRepository.findBySurname(surname));
    }
    /**
     * Trova un singolo utente per indirizzo email.
     *
     * @param email l'indirizzo email da cercare
     * @return il UserDto corrispondente all'email specificata, o null se non trovato
     */
    public UserDto findByEmail(String email)
    {
        return userMapper.toDTO(userRepository.findByEmail(email));
    }
    /**
     * Trova una lista di utenti per nome e cognome.
     *
     * @param name il nome da cercare
     * @param surname il cognome da cercare
     * @return una lista di UserDto corrispondenti al nome e cognome specificati
     */
    public List<UserDto> findByNameAndSurname(String name, String surname)
    {
        return userMapper.toDTOList(userRepository.findByNameAndSurname(name, surname));
    }
    /**
     * Trova un singolo utente per cognome e indirizzo email.
     *
     * @param surname il cognome da cercare
     * @param email l'indirizzo email da cercare
     * @return il UserDto corrispondente al cognome e all'email specificati, o null se non trovato
     */
    public UserDto findBySurnameAndEmail(String surname,String email)
    {
        return userMapper.toDTO(userRepository.findBySurnameAndEmail(surname,email));
    }
}

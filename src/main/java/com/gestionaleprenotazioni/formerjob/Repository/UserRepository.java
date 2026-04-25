package com.gestionaleprenotazioni.formerjob.Repository;

import com.gestionaleprenotazioni.formerjob.Model.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>
{
        //Query JPA , la query viene generata automaticamente , non c'è bisogno di scriverla
        List<User> findByName(String name);
        List<User> findBySurname(String surname);
        User findByEmail(String email);
        User findBySurnameAndEmail(String surname,String email);
        List<User> findByNameAndSurname(String name, String surname);
        Optional<User> findByResetToken(String resetToken);
}

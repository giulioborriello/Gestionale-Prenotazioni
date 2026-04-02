package com.gestionaleprenotazioni.formerjob.Repository;

import com.gestionaleprenotazioni.formerjob.Model.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>
{
        User findByName(String name);
        User findBySurname(String surname);
        User findByEmail(String email);
        User findBySurnameAndEmail(String surname,String email);
        User findByNameAndSurname(String name, String surname);
}

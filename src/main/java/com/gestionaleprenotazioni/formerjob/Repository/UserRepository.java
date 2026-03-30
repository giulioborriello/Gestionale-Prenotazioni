package com.gestionaleprenotazioni.formerjob.Repository;

import com.gestionaleprenotazioni.formerjob.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>
{
        User findByName(String name);
        User findBySurname(String surname);
        User findByEmail(String email);
        User findByTaxCode(String taxCode);

        @Query(value = "SELECT * FROM User u WHERE u.name = ?1 AND u.surname = ?2", nativeQuery = true)
        User findByNameAndSurname(String name, String surname);
}

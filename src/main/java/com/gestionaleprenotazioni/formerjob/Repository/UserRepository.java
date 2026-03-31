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
        User findByTaxCode(String taxCode);

        @Query(value = "SELECT * FROM User u WHERE u.name = ?1 AND u.surname = ?2", nativeQuery = true)
        User findByNameAndSurname(String name, String surname);

        @Query(value = "SELECT * FROM User u WHERE u.surname =?1 AND u.mail = ?2", nativeQuery = true)
        User findBySurnameAndEmail(String surname,String email);

        /*
        @Modifying
        @Transactional
        @Query("UPDATE User u SET u.email = :email WHERE u.id = :id")
        int updateEmail(@Param("id") Integer id, @Param("email") String email);
        */


}

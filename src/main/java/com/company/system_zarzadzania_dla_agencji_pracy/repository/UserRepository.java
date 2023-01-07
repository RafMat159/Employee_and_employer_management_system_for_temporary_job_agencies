package com.company.system_zarzadzania_dla_agencji_pracy.repository;

import com.company.system_zarzadzania_dla_agencji_pracy.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    @Query("SELECT u from User u where u.mail = :mail")
    Optional<User> findUserByMail(@Param("mail") String mail);

    @Query("SELECT u FROM User u")
    List<User> findAll();

    @Query("SELECT u FROM User u WHERE u.idUzytkownika = :idUzytkownika")
    Optional<User> findUserById(@Param("idUzytkownika") Integer idUzytkownika);

    @Modifying
    @Query("DELETE FROM User u WHERE u.idUzytkownika = :idUzytkownika")
    void deleteById(@Param("idUzytkownika") Integer idUzytkownika);
}

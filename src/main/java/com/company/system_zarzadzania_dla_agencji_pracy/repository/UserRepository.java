package com.company.system_zarzadzania_dla_agencji_pracy.repository;

import com.company.system_zarzadzania_dla_agencji_pracy.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {

     @Query("SELECT u from User u where u.mail = :mail")
     Optional<User> findUserByMail(@Param("mail") String mail);


     //jeszcze nie uzywam
     @Query("SELECT u from User u where u.role = :role")
     List<User> findAllByRolesName(@Param("role")String role);

     //jeszcze nie uzywam
     @Query("SELECT u from User u where u.mail = :mail")
     void deleteByMail(@Param("role") String mail);
}

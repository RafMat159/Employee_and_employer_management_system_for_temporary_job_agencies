package com.company.system_zarzadzania_dla_agencji_pracy.repository;

import com.company.system_zarzadzania_dla_agencji_pracy.model.entity.Employer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployerRepository extends JpaRepository<Employer,Integer> {

    @Query("SELECT e FROM Employer e WHERE e.mail =:mail")
    Optional<Employer> findEmployerByMail(@Param("mail") String mail);

    @Query("SELECT e FROM Employer e WHERE e.idUzytkownika =:idUzytkownika")
    Optional<Employer> findEmployerById(@Param("idUzytkownika") Integer idUzytkownika);

    @Query("SELECT e FROM Employer e")
    List<Employer> findAllEmployers();

    @Modifying
    @Query("UPDATE Employer e SET e.currentCosts=:currentCosts WHERE e.idUzytkownika=:idUzytkownika")
    void modifyCurrentCostsValue(@Param("idUzytkownika")Integer idUzytkownika, @Param("currentCosts") Double currentCosts);
}

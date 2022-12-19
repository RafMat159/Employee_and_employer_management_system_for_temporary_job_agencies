package com.company.system_zarzadzania_dla_agencji_pracy.repository;

import com.company.system_zarzadzania_dla_agencji_pracy.model.entity.AgencyEmployee;
import com.company.system_zarzadzania_dla_agencji_pracy.model.entity.Employer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AgencyEmployeeRepository extends JpaRepository<AgencyEmployee, Integer> {

    @Query("SELECT ae FROM AgencyEmployee ae WHERE ae.mail =:mail")
    Optional<AgencyEmployee> findAgencyEmployeeByMail(@Param("mail") String mail);
}

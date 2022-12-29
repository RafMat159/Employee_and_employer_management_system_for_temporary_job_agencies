package com.company.system_zarzadzania_dla_agencji_pracy.repository;

import com.company.system_zarzadzania_dla_agencji_pracy.model.entity.Employee;
import com.company.system_zarzadzania_dla_agencji_pracy.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee,Integer> {

    @Query("SELECT e FROM Employee e WHERE e.mail = :mail")
    Optional<Employee> findEmployeeByMail(@Param("mail") String mail);

    @Query("SELECT e FROM Employee e")
    List<Employee> findAllEmployees();

    @Query("SELECT e FROM Employee e WHERE e.idUzytkownika = :idUzytkownika")
    Optional<Employee> findEmployeeById(@Param("idUzytkownika") Integer idUzytkownika);

    @Query("SELECT DISTINCT e FROM Employee e JOIN e.orders o WHERE o.employer.idUzytkownika = :idUzytkownika AND o.executionDate > :executionDate")
    List<Employee> findEmployeesByOrders(@Param("idUzytkownika") Integer idUzytkownika, @Param("executionDate") Date executionDate);
}

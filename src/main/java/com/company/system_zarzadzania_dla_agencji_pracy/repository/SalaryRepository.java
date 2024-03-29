package com.company.system_zarzadzania_dla_agencji_pracy.repository;

import com.company.system_zarzadzania_dla_agencji_pracy.model.entity.Salary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SalaryRepository extends JpaRepository<Salary, Integer> {

    @Query("SELECT s FROM Salary s JOIN FETCH s.employee")
    List<Salary> findAllSalaries();

    @Query("SELECT s FROM Salary s JOIN FETCH s.employee WHERE s.idPracownika=:idPracownika")
    Optional<Salary> findSalaryById(@Param("idPracownika") Integer idPracownika);

    @Modifying
    @Query("UPDATE Salary s SET s.grossAmount =:grossAmount, s.netSum =:netSum, s.ifPaid =:ifPaid WHERE s.employee.idUzytkownika =:idUzytkownika")
    void newValueOfGrossAmountAndNetSum(@Param("idUzytkownika") Integer idUzytkownika, @Param("grossAmount") Double grossAmount, @Param("netSum") Double netSum, @Param("ifPaid") boolean ifPaid);

    @Modifying
    @Query("UPDATE Salary s SET s.grossAmount =:grossAmount, s.netSum =:netSum WHERE s.employee.idUzytkownika =:idUzytkownika")
    void substractSalary(@Param("idUzytkownika") Integer idUzytkownika, @Param("grossAmount") Double grossAmount, @Param("netSum") Double netSum);

    @Modifying
    @Query("UPDATE Salary s SET s.grossAmount = 0, s.netSum =0 WHERE s.idPracownika=:idPracownika")
    void settleSalaryUpdate(@Param("idPracownika") Integer idPracownika);

    @Modifying
    @Query("UPDATE Salary s SET s.agencyEmployee = null, s.netSum =0 WHERE s.agencyEmployee.idUzytkownika=:idUzytkownika")
    void breakingConnectionWithAgencyEmployee(@Param("idUzytkownika") Integer idUzytkownika);
}

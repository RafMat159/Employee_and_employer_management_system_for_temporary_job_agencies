package com.company.system_zarzadzania_dla_agencji_pracy.repository;

import com.company.system_zarzadzania_dla_agencji_pracy.model.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {

    @Modifying
    @Query("DELETE FROM Order o WHERE o.idZlecenia = :idZlecenia")
    void deleteById(@Param("idZlecenia") Integer idZlecenia);

    @Query("SELECT o FROM Order o")
    List<Order> findAllOrdersRep();

    @Query("SELECT o FROM Order o WHERE o.idZlecenia = :idZlecenia")
    Optional<Order> findOrderById(@Param("idZlecenia") Integer idZlecenia);
}

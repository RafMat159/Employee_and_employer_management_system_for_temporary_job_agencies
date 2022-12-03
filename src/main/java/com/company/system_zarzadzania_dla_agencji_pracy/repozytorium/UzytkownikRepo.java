package com.company.system_zarzadzania_dla_agencji_pracy.repozytorium;

import com.company.system_zarzadzania_dla_agencji_pracy.model.encja.Uzytkownik;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UzytkownikRepo extends JpaRepository<Uzytkownik,Integer> {

    
}

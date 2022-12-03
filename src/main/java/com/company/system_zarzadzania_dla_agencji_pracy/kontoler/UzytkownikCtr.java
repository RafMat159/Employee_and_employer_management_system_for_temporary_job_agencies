package com.company.system_zarzadzania_dla_agencji_pracy.kontoler;

import com.company.system_zarzadzania_dla_agencji_pracy.repozytorium.UzytkownikRepo;
import org.springframework.stereotype.Controller;

@Controller
public class UzytkownikCtr {

    private final UzytkownikRepo uzytkownikRepo;

    public UzytkownikCtr(UzytkownikRepo uzytkownikRepo) {
        this.uzytkownikRepo = uzytkownikRepo;
    }

//    @GetMapping
//    public ModelAndView

    //get & post

}

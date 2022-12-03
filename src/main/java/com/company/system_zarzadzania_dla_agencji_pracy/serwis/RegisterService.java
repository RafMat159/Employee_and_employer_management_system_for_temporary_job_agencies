package com.company.system_zarzadzania_dla_agencji_pracy.serwis;

import com.company.system_zarzadzania_dla_agencji_pracy.model.encja.Uzytkownik;
import com.company.system_zarzadzania_dla_agencji_pracy.model.request.UserRegisterRQ;
import com.company.system_zarzadzania_dla_agencji_pracy.repozytorium.UzytkownikRepo;
import org.springframework.stereotype.Service;

@Service
public class RegisterService {

    private final UzytkownikRepo uzytkownikRepo;

    public RegisterService(UzytkownikRepo uzytkownikRepo) {
        this.uzytkownikRepo = uzytkownikRepo;
    }

    public Uzytkownik createUser(UserRegisterRQ userRegisterRQ){
        return null;
    }

}

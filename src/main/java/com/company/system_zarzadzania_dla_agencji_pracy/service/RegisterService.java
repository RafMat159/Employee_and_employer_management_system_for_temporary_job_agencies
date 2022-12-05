package com.company.system_zarzadzania_dla_agencji_pracy.service;

import com.company.system_zarzadzania_dla_agencji_pracy.model.entity.User;
import com.company.system_zarzadzania_dla_agencji_pracy.model.request.UserRegisterRQ;
import com.company.system_zarzadzania_dla_agencji_pracy.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class RegisterService {

    private final UserRepository userRepository;

    public RegisterService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(UserRegisterRQ userRegisterRQ){
        return null;
    }

}

package com.company.system_zarzadzania_dla_agencji_pracy.service;

import com.company.system_zarzadzania_dla_agencji_pracy.model.entity.User;
import com.company.system_zarzadzania_dla_agencji_pracy.repository.AdministratorRepository;
import com.company.system_zarzadzania_dla_agencji_pracy.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class AdministratorService {

    private UserRepository userRepository;
    private AdministratorRepository administratorRepository; //moze sie przyda

    @Autowired
    public AdministratorService(UserRepository userRepository, AdministratorRepository administratorRepository) {
        this.userRepository = userRepository;
        this.administratorRepository = administratorRepository;
    }

    @Transactional
    public List<User> findAllUsers(){
        return userRepository.findAll();
    }

    @Transactional
    public void deleteUser(Integer id) {
        userRepository.deleteById(id);
    }
}

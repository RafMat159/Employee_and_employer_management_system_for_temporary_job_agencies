package com.company.system_zarzadzania_dla_agencji_pracy.service;

import com.company.system_zarzadzania_dla_agencji_pracy.model.entity.Administrator;
import com.company.system_zarzadzania_dla_agencji_pracy.model.entity.User;
import com.company.system_zarzadzania_dla_agencji_pracy.repository.AdministratorRepository;
import com.company.system_zarzadzania_dla_agencji_pracy.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class AdministratorService {

    private UserRepository userRepository;
    private AdministratorRepository administratorRepository;

    @Autowired
    public AdministratorService(UserRepository userRepository, AdministratorRepository administratorRepository) {
        this.userRepository = userRepository;
        this.administratorRepository = administratorRepository;
    }

    @Transactional
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    @Transactional
    public void deleteUser(Integer id) {
        userRepository.deleteById(id);
    }

    @Transactional
    public Optional<Administrator> checkIfAdministratorIsPresent(String mail) {
        return administratorRepository.findAdministratorByMail(mail);
    }

    @Transactional
    public boolean checkIfUserIsPresent(String enteredMail) {
        Optional<User> userOpt = userRepository.findUserByMail(enteredMail);
        return userOpt.isPresent();
    }
}

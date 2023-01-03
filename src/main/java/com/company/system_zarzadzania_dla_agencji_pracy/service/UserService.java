package com.company.system_zarzadzania_dla_agencji_pracy.service;

import com.company.system_zarzadzania_dla_agencji_pracy.model.entity.Employer;
import com.company.system_zarzadzania_dla_agencji_pracy.model.entity.User;
import com.company.system_zarzadzania_dla_agencji_pracy.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public Optional<User> getUser(String mail) {
        return userRepository.findUserByMail(mail);
    }

    @Transactional
    public Optional<User> getUserById(Integer id) {
        return userRepository.findUserById(id);
    }

    @Transactional
    public void deleteOwnAccount(Integer id) {
        userRepository.deleteById(id);
    }

//    @Transactional
//    public List<User> findAllUsers(){
//        return userRepository.findAll();
//    }

//    @Transactional
//    public Optional<User> findSpecificUser(Integer idUzytkownika){
//        return userRepository.findUserById(idUzytkownika);
//    }

}

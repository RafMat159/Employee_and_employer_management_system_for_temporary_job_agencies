package com.company.system_zarzadzania_dla_agencji_pracy.service;

import com.company.system_zarzadzania_dla_agencji_pracy.model.Role;
import com.company.system_zarzadzania_dla_agencji_pracy.model.entity.Administrator;
import com.company.system_zarzadzania_dla_agencji_pracy.model.entity.Employee;
import com.company.system_zarzadzania_dla_agencji_pracy.model.entity.Employer;
import com.company.system_zarzadzania_dla_agencji_pracy.model.request.EmployerRQ;
import com.company.system_zarzadzania_dla_agencji_pracy.repository.EmployerRepository;
import com.company.system_zarzadzania_dla_agencji_pracy.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class EmployerService {

    PasswordEncoder passwordEncoder;
    EmployerRepository employerRepository;

    @Autowired
    public EmployerService(PasswordEncoder passwordEncoder, EmployerRepository employerRepository) {
        this.passwordEncoder = passwordEncoder;
        this.employerRepository = employerRepository;
    }

    @Transactional
    public void addEmployer(EmployerRQ employerRQ, Administrator administrator) {
        Employer employer = new Employer();

        employer.setCurrentCosts(0.0);
        employer.setAdministrator(administrator);
        employer.setCompanyName(employerRQ.getCompanyName());

        if(employerRQ.getFoundationYear() != null && employerRQ.getFoundationYear() != 0)
            employer.setFoundationYear(employerRQ.getFoundationYear());

        employer.setAddress(employerRQ.getAddress());
        employer.setNIP(employerRQ.getNIP());
        employer.setPhoneNumber(employerRQ.getPhoneNumber());

        employer.setMail(employerRQ.getMail());
        employer.setPassword(passwordEncoder.encode(employerRQ.getPassword()));
        employer.setRole(Role.PRACODAWCA);

        employerRepository.save(employer);
    }

}

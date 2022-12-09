package com.company.system_zarzadzania_dla_agencji_pracy.service;

import com.company.system_zarzadzania_dla_agencji_pracy.model.Role;
import com.company.system_zarzadzania_dla_agencji_pracy.model.entity.Administrator;
import com.company.system_zarzadzania_dla_agencji_pracy.model.entity.AgencyEmployee;
import com.company.system_zarzadzania_dla_agencji_pracy.model.request.AgencyEmployeeRQ;
import com.company.system_zarzadzania_dla_agencji_pracy.repository.AgencyEmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class AgencyEmployeeService {


    private PasswordEncoder passwordEncoder;
    private AgencyEmployeeRepository agencyEmployeeRepository;

    @Autowired
    public AgencyEmployeeService(PasswordEncoder passwordEncoder, AgencyEmployeeRepository agencyEmployeeRepository) {
        this.passwordEncoder = passwordEncoder;
        this.agencyEmployeeRepository = agencyEmployeeRepository;
    }

    @Transactional
    public void addAgencyEmployee(AgencyEmployeeRQ agencyEmployeeRQ, Administrator administrator) {    //dodanie pracownika po walidacji danych
        AgencyEmployee agencyEmployee = new AgencyEmployee();

        agencyEmployee.setName(agencyEmployeeRQ.getName());
        agencyEmployee.setSurname(agencyEmployeeRQ.getSurname());
        agencyEmployee.setPhoneNumber(agencyEmployeeRQ.getPhoneNumber());

        if(agencyEmployeeRQ.getAddress() != null)
            agencyEmployee.setAddress(agencyEmployeeRQ.getAddress());

        agencyEmployee.setPesel(agencyEmployeeRQ.getPesel());
        agencyEmployee.setDateOfBirth(agencyEmployeeRQ.getDateOfBirth());
        agencyEmployee.setAdministrator(administrator);

        agencyEmployee.setMail(agencyEmployeeRQ.getMail());
        agencyEmployee.setPassword(passwordEncoder.encode(agencyEmployeeRQ.getPassword()));
        agencyEmployee.setRole(Role.PRACOWNIKAGENCJI);

        agencyEmployeeRepository.save(agencyEmployee);
    }
}

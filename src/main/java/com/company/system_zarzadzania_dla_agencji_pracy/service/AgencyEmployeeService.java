package com.company.system_zarzadzania_dla_agencji_pracy.service;

import com.company.system_zarzadzania_dla_agencji_pracy.model.Role;
import com.company.system_zarzadzania_dla_agencji_pracy.model.entity.Administrator;
import com.company.system_zarzadzania_dla_agencji_pracy.model.entity.AgencyEmployee;
import com.company.system_zarzadzania_dla_agencji_pracy.model.entity.Employee;
import com.company.system_zarzadzania_dla_agencji_pracy.model.entity.Employer;
import com.company.system_zarzadzania_dla_agencji_pracy.model.request.AgencyEmployeeRQ;
import com.company.system_zarzadzania_dla_agencji_pracy.repository.AgencyEmployeeRepository;
import com.company.system_zarzadzania_dla_agencji_pracy.repository.EmployerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class AgencyEmployeeService {


    private PasswordEncoder passwordEncoder;
    private AgencyEmployeeRepository agencyEmployeeRepository;
    private EmployerRepository employerRepository;

    @Autowired
    public AgencyEmployeeService(PasswordEncoder passwordEncoder, AgencyEmployeeRepository agencyEmployeeRepository, EmployerRepository employerRepository) {
        this.passwordEncoder = passwordEncoder;
        this.agencyEmployeeRepository = agencyEmployeeRepository;
        this.employerRepository = employerRepository;
    }

    @Transactional
    public Optional<AgencyEmployee> getAgencyEmployee(String mail) {
        return agencyEmployeeRepository.findAgencyEmployeeByMail(mail);
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

    @Transactional
    public List<Employer> findAllEmployers(){
        return employerRepository.findAllEmployers();
    }

    @Transactional
    public Optional<Employer> getEmployerById(Integer id){
        return employerRepository.findEmployerById(id);
    }

}

package com.company.system_zarzadzania_dla_agencji_pracy.service;

import com.company.system_zarzadzania_dla_agencji_pracy.model.Role;
import com.company.system_zarzadzania_dla_agencji_pracy.model.entity.Administrator;
import com.company.system_zarzadzania_dla_agencji_pracy.model.entity.Employee;
import com.company.system_zarzadzania_dla_agencji_pracy.model.request.EmployeeRQ;
import com.company.system_zarzadzania_dla_agencji_pracy.repository.AdministratorRepository;
import com.company.system_zarzadzania_dla_agencji_pracy.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class EmployeeService {

    private EmployeeRepository employeeRepository;
    private PasswordEncoder passwordEncoder;
    private AdministratorRepository administratorRepository;

    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository,PasswordEncoder passwordEncoder, AdministratorRepository administratorRepository) {
        this.employeeRepository = employeeRepository;
        this.passwordEncoder = passwordEncoder;
        this.administratorRepository= administratorRepository;
    }


    @Transactional
    public void addEmployee(EmployeeRQ employeeRQ,Administrator administrator) {    //dodanie pracownika po walidacji danych
        Employee employee = new Employee();


        employee.setName(employeeRQ.getName());
        employee.setSurname(employeeRQ.getSurname());
        employee.setPhoneNumber(employeeRQ.getPhoneNumber());

        if(employeeRQ.getAddress() != null)
            employee.setAddress(employeeRQ.getAddress());

        employee.setPesel(employeeRQ.getPesel());
        employee.setDateOfBirth(employeeRQ.getDateOfBirth());
        employee.setAdministrator(administrator);
        employee.setStudentStatus(employeeRQ.isStudentStatus());
        employee.setAvailability(false);                                //domyślnie dostępność pracownika jest ustawiona na false, może to zmienić poprzez naciśnięcie przycisku "Dostępny"

        employee.setMail(employeeRQ.getMail());
        employee.setPassword(passwordEncoder.encode(employeeRQ.getPassword()));
        employee.setRole(Role.PRACOWNIK);

        employeeRepository.save(employee);
    }


}

package com.company.system_zarzadzania_dla_agencji_pracy.service;

import com.company.system_zarzadzania_dla_agencji_pracy.model.Role;
import com.company.system_zarzadzania_dla_agencji_pracy.model.entity.Administrator;
import com.company.system_zarzadzania_dla_agencji_pracy.model.entity.Document;
import com.company.system_zarzadzania_dla_agencji_pracy.model.entity.Employee;
import com.company.system_zarzadzania_dla_agencji_pracy.model.entity.Employer;
import com.company.system_zarzadzania_dla_agencji_pracy.model.request.DocumentRQ;
import com.company.system_zarzadzania_dla_agencji_pracy.model.request.EmployeeRQ;
import com.company.system_zarzadzania_dla_agencji_pracy.repository.AdministratorRepository;
import com.company.system_zarzadzania_dla_agencji_pracy.repository.DocumentRepository;
import com.company.system_zarzadzania_dla_agencji_pracy.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class EmployeeService {

    private EmployeeRepository employeeRepository;
    private PasswordEncoder passwordEncoder;
    private AdministratorRepository administratorRepository;
    DocumentRepository documentRepository;

    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository,PasswordEncoder passwordEncoder, AdministratorRepository administratorRepository, DocumentRepository documentRepository) {
        this.employeeRepository = employeeRepository;
        this.passwordEncoder = passwordEncoder;
        this.administratorRepository= administratorRepository;
        this.documentRepository = documentRepository;
    }


    @Transactional
    public Optional<Employee> getEmployee(String mail) {
        return employeeRepository.findEmployeeByMail(mail);
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

        employee.setMail(employeeRQ.getMail());
        employee.setPassword(passwordEncoder.encode(employeeRQ.getPassword()));
        employee.setRole(Role.PRACOWNIK);

        employeeRepository.save(employee);
    }

    @Transactional
    public void addNewDocumentEmployee(DocumentRQ documentRQ, Employee employee){
        Document document = new Document();
        document.setDocumentType(documentRQ.getDocumentType());
        document.setContent(documentRQ.getContent());
        document.setEmployer(null);
        document.setEmployee(employee);
        documentRepository.save(document);
    }

    @Transactional
    public Optional<Document> getDocumentEmployee(Integer idDokumentu){
        return documentRepository.findById(idDokumentu);
    }

    @Transactional
    public void deleteDocumentEmployee(Integer id) {
        documentRepository.deleteById(id);
    }
}

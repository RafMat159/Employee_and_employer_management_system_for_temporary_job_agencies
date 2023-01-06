package com.company.system_zarzadzania_dla_agencji_pracy.service;

import com.company.system_zarzadzania_dla_agencji_pracy.converter.TimeConverter;
import com.company.system_zarzadzania_dla_agencji_pracy.model.Role;
import com.company.system_zarzadzania_dla_agencji_pracy.model.entity.*;
import com.company.system_zarzadzania_dla_agencji_pracy.model.request.DocumentRQ;
import com.company.system_zarzadzania_dla_agencji_pracy.model.request.EmployeeRQ;
import com.company.system_zarzadzania_dla_agencji_pracy.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {

    private EmployeeRepository employeeRepository;
    private PasswordEncoder passwordEncoder;
    private DocumentRepository documentRepository;
    private OrderRepository orderRepository;
    private EmployerRepository employerRepository;
    private AdministratorRepository administratorRepository;
    private SalaryRepository salaryRepository;

    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository,PasswordEncoder passwordEncoder, AdministratorRepository administratorRepository, DocumentRepository documentRepository, OrderRepository orderRepository, EmployerRepository employerRepository, SalaryRepository salaryRepository) {
        this.employeeRepository = employeeRepository;
        this.passwordEncoder = passwordEncoder;
        this.administratorRepository= administratorRepository;
        this.documentRepository = documentRepository;
        this.orderRepository = orderRepository;
        this.employerRepository = employerRepository;
        this.salaryRepository = salaryRepository;
    }


    @Transactional
    public Optional<Employee> getEmployee(String mail) {
        return employeeRepository.findEmployeeByMail(mail);
    }

    @Transactional
    public void addEmployee(EmployeeRQ employeeRQ,Administrator administrator) {    //dodanie pracownika po walidacji danych
        Employee employee = new Employee();
        Salary salary  = new Salary();

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

        //tworzenie salary dla kazdego pracownika osobno
        salary.setAgencyEmployee(null);
        salary.setEmployee(employee);
        salary.setIfPaid(false);
        salary.setNetSum(0.0);
        salary.setGrossAmount(0.0);
        employee.setSalary(salary);
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

    @Transactional
    public List<Order> findAllOrders(){
        return orderRepository.findAllOrdersRep();
    }

    @Transactional
    public Optional<Employer> getEmployerById(Integer id) {
        return employerRepository.findEmployerById(id);
    }

    @Transactional
    public Optional<Order> findOrderEmployee(Integer id){
        return orderRepository.findOrderById(id);
    }

    @Transactional
    public Employee saveOrder(Order order, Employee employee){
        employee.addOrder(order);
        return employeeRepository.save(employee);
    }

    @Transactional
    public void substractSalary(Order order, Employee employee){
        Salary salary = employee.getSalary();
        salary.setIfPaid(false);

        BigDecimal grossAmount = TimeConverter.getGrossAmount(order);

        Employer employer = order.getEmployer();
        BigDecimal currentCosts = BigDecimal.valueOf(employer.getCurrentCosts());
        employerRepository.modifyCurrentCostsValue(order.getEmployer().getIdUzytkownika(),currentCosts.subtract(grossAmount).doubleValue());

        salary.setGrossAmount(salary.getGrossAmount() - grossAmount.doubleValue());
        if(employee.isStudentStatus())                      //jesli student, to taka sama kwota netto co brutto
            salary.setNetSum(salary.getNetSum() - grossAmount.doubleValue());
        else
            salary.setNetSum(salary.getNetSum() - countNetSumFromGrossAmount(grossAmount));  //jesli nie to liczymy

        salaryRepository.substractSalary(employee.getIdUzytkownika(),salary.getGrossAmount(), salary.getNetSum());
    }


    @Transactional
    public void addSalary(Order order, Employee employee){
        Salary salary = employee.getSalary();
        salary.setIfPaid(false);

        BigDecimal grossAmount = TimeConverter.getGrossAmount(order);

        Employer employer = order.getEmployer();
        BigDecimal currentCosts = BigDecimal.valueOf(employer.getCurrentCosts());
        employerRepository.modifyCurrentCostsValue(order.getEmployer().getIdUzytkownika(),currentCosts.add(grossAmount).doubleValue());

        salary.setGrossAmount(salary.getGrossAmount() + grossAmount.doubleValue());
        if(employee.isStudentStatus())                      //jesli student, to taka sama kwota netto co brutto
            salary.setNetSum(salary.getNetSum()+ grossAmount.doubleValue());
        else
            salary.setNetSum(salary.getNetSum()+ countNetSumFromGrossAmount(grossAmount));  //jesli nie to liczymy

        salaryRepository.newValueOfGrossAmountAndNetSum(employee.getIdUzytkownika(),salary.getGrossAmount(), salary.getNetSum(),salary.isIfPaid());
    }

    private Double countNetSumFromGrossAmount(BigDecimal grossAmount){
        BigDecimal divisor = new BigDecimal("1.23");
        return grossAmount.divide(divisor,2,RoundingMode.HALF_UP).doubleValue();
    }

    @Transactional
    public Employee removeOrderEmployee(Order order, Employee employee){
        employee.removeOrder(order);
        return employeeRepository.save(employee);
    }

    @Transactional
    public void settleSalary(Salary salary){
        salary.setNetSum(0.0);
        salary.setGrossAmount(0.0);
        salaryRepository.settleSalaryUpdate(salary.getIdWynagrodzenia());
    }

}

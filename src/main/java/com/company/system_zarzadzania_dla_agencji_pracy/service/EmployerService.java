package com.company.system_zarzadzania_dla_agencji_pracy.service;

import com.company.system_zarzadzania_dla_agencji_pracy.converter.TimeConverter;
import com.company.system_zarzadzania_dla_agencji_pracy.model.Role;
import com.company.system_zarzadzania_dla_agencji_pracy.model.entity.*;
import com.company.system_zarzadzania_dla_agencji_pracy.model.request.DocumentRQ;
import com.company.system_zarzadzania_dla_agencji_pracy.model.request.EmployerRQ;
import com.company.system_zarzadzania_dla_agencji_pracy.model.request.OrderRQ;
import com.company.system_zarzadzania_dla_agencji_pracy.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class EmployerService {

    private PasswordEncoder passwordEncoder;
    private EmployerRepository employerRepository;
    private EmployeeRepository employeeRepository;
    private OrderRepository orderRepository;
    private DocumentRepository documentRepository;
    private UserRepository userRepository;

    @Autowired
    public EmployerService(PasswordEncoder passwordEncoder, EmployerRepository employerRepository, OrderRepository orderRepository, DocumentRepository documentRepository, UserRepository userRepository, EmployeeRepository employeeRepository) {
        this.passwordEncoder = passwordEncoder;
        this.employerRepository = employerRepository;
        this.orderRepository = orderRepository;
        this.documentRepository = documentRepository;
        this.userRepository = userRepository;
        this.employeeRepository = employeeRepository;
    }

    @Transactional
    public void addEmployer(EmployerRQ employerRQ, Administrator administrator) {
        Employer employer = new Employer();

        employer.setCurrentCosts(0.0);
        employer.setAdministrator(administrator);
        employer.setCompanyName(employerRQ.getCompanyName());

        if (employerRQ.getFoundationYear() != null && employerRQ.getFoundationYear() != 0)
            employer.setFoundationYear(employerRQ.getFoundationYear());

        employer.setAddress(employerRQ.getAddress());
        employer.setNIP(employerRQ.getNIP());
        employer.setPhoneNumber(employerRQ.getPhoneNumber());

        employer.setMail(employerRQ.getMail());
        employer.setPassword(passwordEncoder.encode(employerRQ.getPassword()));
        employer.setRole(Role.PRACODAWCA);

        employerRepository.save(employer);
    }

    @Transactional
    public Optional<Employer> getEmployer(String mail) {
        return employerRepository.findEmployerByMail(mail);
    }

    @Transactional
    public void addNewOrder(OrderRQ orderRQ, Employer employer) {
        Order order = new Order();
        Date date = Date.valueOf(LocalDate.now());          //ustawienie biezacej daty
        order.setExecutionDate(orderRQ.getExecutionDate());
        order.setAvailabilityDate(date);
        order.setPerformancePlace(orderRQ.getPerformancePlace());
        order.setWorkNature(orderRQ.getWorkNature());
        order.setWorkingHours(orderRQ.getWorkingHours());
        order.setHourlyRate(orderRQ.getHourlyRate());
        order.setVacanciesNumber(orderRQ.getVacanciesNumber());
        order.setEmployer(employer);
        order.setSettled(false);

        orderRepository.save(order);
    }

    @Transactional
    public void addNewDocumentEmployer(DocumentRQ documentRQ, Employer employer) {
        Document document = new Document();
        document.setDocumentType(documentRQ.getDocumentType());
        document.setContent(documentRQ.getContent());
        document.setEmployer(employer);
        document.setEmployee(null);
        documentRepository.save(document);
    }


    @Transactional
    public Optional<Document> getDocumentEmployer(Integer idDokumentu) {
        return documentRepository.findById(idDokumentu);
    }

    @Transactional
    public void deleteDocumentEmployer(Integer id) {
        documentRepository.deleteById(id);
    }

    @Transactional
    public void deleteOrder(Integer id) {
        orderRepository.deleteById(id);
    }

    @Transactional
    public List<Employee> findAllEmployees() {
        return employeeRepository.findAllEmployees();
    }

    @Transactional
    public Optional<Employee> getEmployeeById(Integer id) {
        return employeeRepository.findEmployeeById(id);
    }

    @Transactional
    public List<Employee> findCurrentlyEmployedEmployees(Integer id, Date currDate) {
        return employeeRepository.findEmployeesByOrders(id, currDate);
    }

    @Transactional
    public Optional<Order> findOrderEmployer(Integer id) {
        return orderRepository.findOrderById(id);
    }

    @Transactional
    public List<Order> findAllOrdersByEmployerId(Integer id) {
        return orderRepository.findAllOrdersByEmployerId(id);
    }

    @Transactional
    public Employee removeEmployeeFromOrder(Employer employer, Order order, Employee employee) {
        BigDecimal grossAmount = TimeConverter.getGrossAmount(order);
        BigDecimal currentCostsBD = BigDecimal.valueOf(employer.getCurrentCosts());
        employerRepository.modifyCurrentCostsValue(order.getEmployer().getIdUzytkownika(), currentCostsBD.subtract(grossAmount).doubleValue());
        employee.removeOrder(order);
        return employeeRepository.save(employee);
    }

    @Transactional
    public void refreshCurrentCosts(Employer employer) {
        List<Order> orders = employer.getOrders();
        boolean modified = false;
        for (int i = 0; i < orders.size(); i++) {
            Order order = orders.get(i);
            if (order.getExecutionDate().compareTo(Date.valueOf(LocalDate.now())) <= 0 && !order.isSettled()) {
                BigDecimal numberOfEmployees = new BigDecimal(order.getEmployees().size());
                BigDecimal grossAmount = TimeConverter.getGrossAmount(order);
                BigDecimal currentCostsBD = BigDecimal.valueOf(employer.getCurrentCosts());
                employer.setCurrentCosts(currentCostsBD.subtract(grossAmount.multiply(numberOfEmployees)).doubleValue());
                order.setSettled(true);
                modified = true;
            }
        }
        if (modified) {
            employer.setOrders(orders);
            employerRepository.save(employer);
        }
    }
}

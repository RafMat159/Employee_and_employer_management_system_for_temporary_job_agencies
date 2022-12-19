package com.company.system_zarzadzania_dla_agencji_pracy.service;

import com.company.system_zarzadzania_dla_agencji_pracy.model.Role;
import com.company.system_zarzadzania_dla_agencji_pracy.model.entity.Administrator;
import com.company.system_zarzadzania_dla_agencji_pracy.model.entity.Document;
import com.company.system_zarzadzania_dla_agencji_pracy.model.entity.Employer;
import com.company.system_zarzadzania_dla_agencji_pracy.model.entity.Order;
import com.company.system_zarzadzania_dla_agencji_pracy.model.request.DocumentRQ;
import com.company.system_zarzadzania_dla_agencji_pracy.model.request.EmployerRQ;
import com.company.system_zarzadzania_dla_agencji_pracy.model.request.OrderRQ;
import com.company.system_zarzadzania_dla_agencji_pracy.repository.DocumentRepository;
import com.company.system_zarzadzania_dla_agencji_pracy.repository.EmployerRepository;
import com.company.system_zarzadzania_dla_agencji_pracy.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Optional;

@Service
public class EmployerService {

    PasswordEncoder passwordEncoder;
    EmployerRepository employerRepository;
    OrderRepository orderRepository;
    DocumentRepository documentRepository;

    @Autowired
    public EmployerService(PasswordEncoder passwordEncoder, EmployerRepository employerRepository, OrderRepository orderRepository, DocumentRepository documentRepository) {
        this.passwordEncoder = passwordEncoder;
        this.employerRepository = employerRepository;
        this.orderRepository = orderRepository;
        this.documentRepository = documentRepository;
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

    @Transactional
    public Optional<Employer> getEmployer(String mail) {
        return employerRepository.findEmployerByMail(mail);
    }

    @Transactional
    public void addNewOrder(OrderRQ orderRQ, Employer employer){
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

        orderRepository.save(order);
    }

    @Transactional
    public void addNewDocumentEmployer(DocumentRQ documentRQ, Employer employer){
        Document document = new Document();
        document.setDocumentType(documentRQ.getDocumentType());
        document.setContent(documentRQ.getContent());
        document.setEmployer(employer);
        document.setEmployee(null);
        documentRepository.save(document);
    }


    @Transactional
    public Optional<Document> getDocumentEmployer(Integer idDokumentu){
        return documentRepository.findById(idDokumentu);
    }

    @Transactional
    public void deleteDocumentEmployer(Integer id) {
        documentRepository.deleteById(id);
    }
}

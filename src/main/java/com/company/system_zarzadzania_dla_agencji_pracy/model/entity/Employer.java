//package com.company.system_zarzadzania_dla_agencji_pracy.model.entity;
//
//import org.springframework.lang.NonNull;
//
//import javax.persistence.*;
//import java.util.Date;
//import java.util.Set;
//
//@Entity
//@Table(name = "pracodawca")
//public class Employer extends User{
//
//
//    private String nazwaFirmy;
//
//    private Date rokZalozenia;
//
//    private String adresSiedziby;
//
//    private String NIP;
//
//    private String nrTelefonu;
//
//    private Double biezaceKoszty;
//
//
//    @ManyToOne()
//    @JoinColumn(name = "idAdministratora", nullable = false)
//    private Administrator administrator;
//
//    @OneToMany(mappedBy = "pracodawca")
//    private Set<Order> zlecenia;
//
//    @OneToMany(mappedBy = "pracodawca")
//    private Set<Document> dokumenty;
//
//}

//package com.company.system_zarzadzania_dla_agencji_pracy.model.entity;
//
//import org.springframework.lang.NonNull;
//
//import javax.persistence.*;
//import java.util.Date;
//import java.util.Set;
//
//
//@Entity
//@Table(name="pracownikagencji")
//public class AgencyEmployee extends User {
//
//
//    @NonNull
//    private String imie;
//
//    @NonNull
//    private String nazwisko;
//
//    @NonNull
//    private String nrTelefonu;
//
//
//    private String adresZamieszkania;
//
//    @NonNull
//    private String pesel;
//
//    @NonNull
//    private Date dataUrodzenia;
//
//    @ManyToOne()
//    @JoinColumn(name = "idAdministratora", nullable = false)
//    private Administrator administrator;
//
//    @OneToMany(mappedBy = "pracownikAgencji")
//    private Set<Salary> wynagrodzenia;
//
//    @OneToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name = "idUzytkownika", referencedColumnName = "idUzytkownika")
//    private User uzytkownik;
//
//}

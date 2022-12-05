package com.company.system_zarzadzania_dla_agencji_pracy.model.entity;

import org.springframework.lang.NonNull;

import javax.persistence.*;
import java.sql.Date;
import java.util.Set;

@Entity
@Table(name = "pracownicy")
public class Employee {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer idPracownika;

    @NonNull
    private String imie;

    //@NonNull
    private String nazwisko;

    //@NonNull
    private String nrTelefonu;


    private String adresZamieszkania;

    //@NonNull
    private String pesel;

    @NonNull
    private boolean statusStudenta;

    @NonNull
    private Date dataUrodzenia;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "idUzytkownika", referencedColumnName = "idUzytkownika")
    private User uzytkownik;

    @ManyToOne()
    @JoinColumn(name = "idAdministratora", nullable = false)
    private Administrator administrator;

    @OneToOne(mappedBy = "pracownik")
    private Salary wynagrodzenie;
    //TODO CZY TUTAJ SIE DA JAKOS OKRESLIC NULL LUB NOT NULL
    @OneToMany(mappedBy = "pracownik")
    private Set<Document> dokumenty;

    @ManyToMany(mappedBy = "pracownicy")
    Set<Order> zlecenia;

}

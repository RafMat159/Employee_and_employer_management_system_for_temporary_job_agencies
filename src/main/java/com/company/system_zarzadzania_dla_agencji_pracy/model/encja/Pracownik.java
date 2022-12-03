package com.company.system_zarzadzania_dla_agencji_pracy.model.encja;

import org.springframework.lang.NonNull;

import javax.persistence.*;
import java.sql.Date;
import java.util.Set;

@Entity
@Table(name = "pracownicy")
public class Pracownik {

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
    private Uzytkownik uzytkownik;

    @ManyToOne()
    @JoinColumn(name = "idAdministratora", nullable = false)
    private Administrator administrator;

    @OneToOne(mappedBy = "pracownik")
    private Wynagrodzenie wynagrodzenie;
    //TODO CZY TUTAJ SIE DA JAKOS OKRESLIC NULL LUB NOT NULL
    @OneToMany(mappedBy = "pracownik")
    private Set<Dokument> dokumenty;

    @ManyToMany(mappedBy = "pracownicy")
    Set<Zlecenie> zlecenia;

}

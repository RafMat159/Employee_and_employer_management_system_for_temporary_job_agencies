package com.company.system_zarzadzania_dla_agencji_pracy;

import jakarta.persistence.*;
import org.springframework.lang.NonNull;

import java.util.Date;
import java.util.Set;

@Entity
@Table(name="pracownikagencji")
public class PracownikAgencji {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer idPracownikaAgencji;


    @NonNull
    private String imie;

    @NonNull
    private String nazwisko;

    @NonNull
    private String nrTelefonu;


    private String adresZamieszkania;

    @NonNull
    private String pesel;

    @NonNull
    private Date dataUrodzenia;

    @NonNull
    private Integer idAdministratora;

    @NonNull
    private Integer idUzytkownika;

    @ManyToOne()
    @JoinColumn(name = "idAdministratora", nullable = false)
    private Administrator administrator;

    @OneToMany(mappedBy = "administrator")
    private Set<Wynagrodzenie> wynagrodzenia;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "idUzytkownika", referencedColumnName = "idUzytkownika")
    private Uzytkownik uzytkownik;

}

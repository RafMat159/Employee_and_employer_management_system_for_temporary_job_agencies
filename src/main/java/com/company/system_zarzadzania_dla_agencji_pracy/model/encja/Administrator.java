package com.company.system_zarzadzania_dla_agencji_pracy.model.encja;


import org.springframework.lang.NonNull;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "administrator")
public class Administrator {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer idAdministratora;

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

    @OneToMany(mappedBy = "administrator")
    private Set<PracownikAgencji> pracownicyAgencji;

    @OneToMany(mappedBy = "administrator")
    private Set<Pracownik> pracownicy;

    @OneToMany(mappedBy = "administrator")
    private Set<Pracodawca> pracodawcy;


    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "idUzytkownika", referencedColumnName = "idUzytkownika")
    private Uzytkownik uzytkownik;

}

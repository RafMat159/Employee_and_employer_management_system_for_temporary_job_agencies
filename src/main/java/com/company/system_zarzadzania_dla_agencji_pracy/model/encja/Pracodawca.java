package com.company.system_zarzadzania_dla_agencji_pracy.model.encja;

import org.springframework.lang.NonNull;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "pracodawca")
public class Pracodawca {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer idPracodawca;

    @NonNull
    private String nazwaFirmy;

    private Date rokZalozenia;

    @NonNull
    private String adresSiedziby;

    @NonNull
    private String NIP;

    @NonNull
    private String nrTelefonu;

    @NonNull
    private Double biezaceKoszty;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "idUzytkownika", referencedColumnName = "idUzytkownika")
    private Uzytkownik uzytkownik;

    @ManyToOne()
    @JoinColumn(name = "idAdministratora", nullable = false)
    private Administrator administrator;

    @OneToMany(mappedBy = "pracodawca")
    private Set<Zlecenie> zlecenia;

    @OneToMany(mappedBy = "pracodawca")
    private Set<Dokument> dokumenty;

}

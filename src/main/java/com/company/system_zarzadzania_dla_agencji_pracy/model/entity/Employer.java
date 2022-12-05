package com.company.system_zarzadzania_dla_agencji_pracy.model.entity;

import org.springframework.lang.NonNull;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "pracodawca")
public class Employer {

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
    private User uzytkownik;

    @ManyToOne()
    @JoinColumn(name = "idAdministratora", nullable = false)
    private Administrator administrator;

    @OneToMany(mappedBy = "pracodawca")
    private Set<Order> zlecenia;

    @OneToMany(mappedBy = "pracodawca")
    private Set<Document> dokumenty;

}

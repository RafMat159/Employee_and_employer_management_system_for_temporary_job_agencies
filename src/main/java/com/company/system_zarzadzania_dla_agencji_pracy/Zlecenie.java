package com.company.system_zarzadzania_dla_agencji_pracy;

import jakarta.persistence.*;
import org.springframework.lang.NonNull;

import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "zlecenie")
public class Zlecenie {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer idZlecenia;

    @NonNull
    private Date dataWykonania;

    @NonNull
    private Date dataUdostepnienia;

    @NonNull
    private String miejsceWykonania;

    @NonNull
    private String charakterPracy;

    @NonNull
    private String godzinyPracy;

    @NonNull
    private Double stawkaGodzinowa;

    @ManyToOne()
    @JoinColumn(name = "idPracodawcy", nullable = false)
    private Pracodawca pracodawca;

    @ManyToMany
    @JoinTable(name="zlecenie_pracownik",
    joinColumns = @JoinColumn(name = "idZlecenia"), inverseJoinColumns = @JoinColumn(name = "idPracownika"))
    Set<Pracownik> pracownicy;
}

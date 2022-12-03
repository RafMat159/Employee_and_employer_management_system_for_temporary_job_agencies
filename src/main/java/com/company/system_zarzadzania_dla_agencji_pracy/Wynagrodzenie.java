package com.company.system_zarzadzania_dla_agencji_pracy;


import jakarta.persistence.*;
import org.springframework.lang.NonNull;

@Entity
@Table(name = "wynagrodzenie")
public class Wynagrodzenie {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer idWynagrodzenia;

    @NonNull
    private Double kwotaNetto;

    @NonNull
    private Double kwotaBrutto;

    @NonNull
    private boolean czyWyplacone;

    @ManyToOne()
    @JoinColumn(name = "idPracownikaAgencji", nullable = true)
    private PracownikAgencji pracownikAgencji;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "idPracownika", referencedColumnName = "idPracownika")
    private Pracownik pracownik;

}

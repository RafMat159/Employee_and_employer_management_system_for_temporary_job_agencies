package com.company.system_zarzadzania_dla_agencji_pracy;

import jakarta.persistence.*;
import org.springframework.lang.NonNull;

import java.util.Date;

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

    @NonNull
    private Integer idPracodawcy;

    @ManyToOne()
    @JoinColumn(name = "idPracodawcy", nullable = false)
    private Pracodawca pracodawca;

    //TODO MANY TO MANY ZLECENIE PRACOWNIK
}

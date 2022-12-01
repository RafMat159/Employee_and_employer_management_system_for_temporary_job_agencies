package com.company.system_zarzadzania_dla_agencji_pracy;

import jakarta.persistence.*;
import org.springframework.lang.NonNull;

@Entity
@Table(name = "opiniaopracodawcy")
public class OpiniaOPracodawcy {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer idOpinii;

    @NonNull
    private Integer idPracodawcy;

    @NonNull
    private Integer idPracownika;

    @NonNull
    private boolean czyPoleca;

    @ManyToOne()
    @JoinColumn(name = "idPracodawcy", nullable = false)
    private Pracodawca pracodawca;

    @ManyToOne()
    @JoinColumn(name = "idPracownika", nullable = false)
    private Pracownik pracownik;
}

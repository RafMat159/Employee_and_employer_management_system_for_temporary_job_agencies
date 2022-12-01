package com.company.system_zarzadzania_dla_agencji_pracy;


import jakarta.persistence.*;
import org.springframework.lang.NonNull;

@Entity
@Table(name = "dokument")
public class Dokument {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer idDokumentu;

    @NonNull
    private String typDokumentu;

    @NonNull
    private String tresc;

    private Integer idPracodawcy;

    private Integer idPracownika;

    @ManyToOne()
    @JoinColumn(name = "idPracodawcy", nullable = true)
    private Pracodawca pracodawca;

    @ManyToOne()
    @JoinColumn(name = "idPracownika", nullable = true)
    private Pracownik pracownik;


}

package com.company.system_zarzadzania_dla_agencji_pracy.model.encja;


import org.springframework.lang.NonNull;

import javax.persistence.*;

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

    @ManyToOne()
    @JoinColumn(name = "idPracodawcy", nullable = true)
    private Pracodawca pracodawca;

    @ManyToOne()
    @JoinColumn(name = "idPracownika", nullable = true)
    private Pracownik pracownik;


}

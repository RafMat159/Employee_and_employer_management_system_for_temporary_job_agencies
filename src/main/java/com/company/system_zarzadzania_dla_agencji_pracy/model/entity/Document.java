//package com.company.system_zarzadzania_dla_agencji_pracy.model.entity;
//
//
//import org.springframework.lang.NonNull;
//
//import javax.persistence.*;
//
//@Entity
//@Table(name = "dokument")
//public class Document {
//
//    @Id
//    @GeneratedValue(strategy= GenerationType.IDENTITY)
//    private Integer idDokumentu;
//
//    @NonNull
//    private String typDokumentu;
//
//    @NonNull
//    private String tresc;
//
//    @ManyToOne()
//    @JoinColumn(name = "idPracodawcy", nullable = true)
//    private Employer pracodawca;
//
//    @ManyToOne()
//    @JoinColumn(name = "idPracownika", nullable = true)
//    private Employee pracownik;
//
//
//}

//package com.company.system_zarzadzania_dla_agencji_pracy.model.entity;
//
//
//import org.springframework.lang.NonNull;
//
//import javax.persistence.*;
//
//@Entity
//@Table(name = "wynagrodzenie")
//public class Salary {
//
//    @Id
//    @GeneratedValue(strategy= GenerationType.IDENTITY)
//    private Integer idWynagrodzenia;
//
//    @NonNull
//    private Double kwotaNetto;
//
//    @NonNull
//    private Double kwotaBrutto;
//
//    @NonNull
//    private boolean czyWyplacone;
//
//    @ManyToOne()
//    @JoinColumn(name = "idPracownikaAgencji", nullable = true)
//    private AgencyEmployee pracownikAgencji;
//
//    @OneToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name = "idPracownika", referencedColumnName = "idPracownika")
//    private Employee pracownik;
//
//}

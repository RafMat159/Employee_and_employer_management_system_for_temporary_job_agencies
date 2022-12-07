//package com.company.system_zarzadzania_dla_agencji_pracy.model.entity;
//
//import org.springframework.lang.NonNull;
//
//import javax.persistence.*;
//import java.util.Date;
//import java.util.Set;
//
//@Entity
//@Table(name = "zlecenie")
//public class Order {
//
//    @Id
//    @GeneratedValue(strategy= GenerationType.IDENTITY)
//    private Integer idZlecenia;
//
//    @NonNull
//    private Date dataWykonania;
//
//    @NonNull
//    private Date dataUdostepnienia;
//
//    @NonNull
//    private String miejsceWykonania;
//
//    @NonNull
//    private String charakterPracy;
//
//    @NonNull
//    private String godzinyPracy;
//
//    @NonNull
//    private Double stawkaGodzinowa;
//
//    @ManyToOne()
//    @JoinColumn(name = "idPracodawcy", nullable = false)
//    private Employer pracodawca;
//
//    @ManyToMany
//    @JoinTable(name="zlecenie_pracownik",
//    joinColumns = @JoinColumn(name = "idZlecenia"), inverseJoinColumns = @JoinColumn(name = "idPracownika"))
//    Set<Employee> pracownicy;
//}

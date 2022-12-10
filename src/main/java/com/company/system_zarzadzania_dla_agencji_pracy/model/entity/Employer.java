package com.company.system_zarzadzania_dla_agencji_pracy.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Setter
@PrimaryKeyJoinColumn(name = "idUzytkownika")
@Table(name = "pracodawca")
public class Employer extends User{

    @Column(name = "nazwaFirmy")
    private String companyName;

    @Column(name = "rokZalozenia")
    private Integer foundationYear;

    @Column(name = "adresSiedziby")
    private String address;

    @Column(name = "NIP")
    private String NIP;

    @Column(name = "nrTelefonu")
    private String phoneNumber;

    @Column(name = "biezaceKoszty")
    private Double currentCosts;
//
//
    @ManyToOne()
    @JoinColumn(name = "idAdministratora")
    private Administrator administrator;
//
//    @OneToMany(mappedBy = "pracodawca")
//    private Set<Order> zlecenia;
//
//    @OneToMany(mappedBy = "pracodawca")
//    private Set<Document> dokumenty;
//
}

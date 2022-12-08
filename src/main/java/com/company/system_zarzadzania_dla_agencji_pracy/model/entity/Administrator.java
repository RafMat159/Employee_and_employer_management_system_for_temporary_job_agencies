package com.company.system_zarzadzania_dla_agencji_pracy.model.entity;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Date;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "administrator")
@PrimaryKeyJoinColumn(name = "idUzytkownika")
public class Administrator extends User{


    @Column(name = "imie")
    private String name;

    @Column(name = "nazwisko")
    private String surname;

    @Column(name = "nrTelefonu")
    private String phoneNumber;

    @Column(name = "adresZamieszkania")
    private String address;

    @Column(name = "pesel")
    private String pesel;

    @Column(name = "dataUrodzenia")
    private Date dateOfBirth;

//    @OneToMany(mappedBy = "administrator")
//    private Set<AgencyEmployee> pracownicyAgencji;
//
    @OneToMany(mappedBy = "administrator")
    private Set<Employee> pracownicy;
//
    @OneToMany(mappedBy = "administrator")
    private Set<Employer> pracodawcy;
//
//
//    @OneToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name = "idUzytkownika", referencedColumnName = "idUzytkownika")
//    private User uzytkownik;

}

package com.company.system_zarzadzania_dla_agencji_pracy.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.sql.Date;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@OnDelete(action = OnDeleteAction.CASCADE)
@Table(name="pracownikagencji")
public class AgencyEmployee extends User {

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

    @ManyToOne()
    @JoinColumn(name = "idAdministratora")
    private Administrator administrator;
//
//    @OneToMany(mappedBy = "pracownikAgencji")
//    private Set<Salary> wynagrodzenia;
//
//    @OneToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name = "idUzytkownika", referencedColumnName = "idUzytkownika")
//    private User uzytkownik;
//
}

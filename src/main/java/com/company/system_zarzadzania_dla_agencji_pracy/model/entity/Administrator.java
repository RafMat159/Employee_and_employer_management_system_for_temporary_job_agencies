package com.company.system_zarzadzania_dla_agencji_pracy.model.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@OnDelete(action = OnDeleteAction.CASCADE)
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

    @OneToMany(mappedBy = "administrator",fetch = FetchType.LAZY,cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AgencyEmployee> agencyEmployees;

    @OneToMany(mappedBy = "administrator",fetch = FetchType.LAZY,cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Employee> employees;

    @OneToMany(mappedBy = "administrator", fetch = FetchType.LAZY,cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Employer> employers;
//
//
//    @OneToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name = "idUzytkownika", referencedColumnName = "idUzytkownika")
//    private User uzytkownik;

}

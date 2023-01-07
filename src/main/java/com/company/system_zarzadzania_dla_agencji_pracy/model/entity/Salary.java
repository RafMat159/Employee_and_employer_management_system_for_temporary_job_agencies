package com.company.system_zarzadzania_dla_agencji_pracy.model.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.NonNull;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Table(name = "wynagrodzenie")
public class Salary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idWynagrodzenia")
    private Integer idWynagrodzenia;

    @NonNull
    @Column(name = "kwotaNetto")
    private Double netSum;

    @NonNull
    @Column(name = "kwotaBrutto")
    private Double grossAmount;

    @NonNull
    @Column(name = "czyWyplacone")
    private boolean ifPaid;


    @ManyToOne(optional = true)
    @JoinColumn(name = "idPracownikaAgencji", referencedColumnName = "idUzytkownika")
    private AgencyEmployee agencyEmployee;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "idPracownika", referencedColumnName = "idUzytkownika")
    private Employee employee;

}

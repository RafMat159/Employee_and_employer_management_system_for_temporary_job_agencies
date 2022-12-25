package com.company.system_zarzadzania_dla_agencji_pracy.model.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Table(name = "zlecenie")
public class Order {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "idZlecenia")
    private Integer idZlecenia;

    @Column(name = "dataWykonania")
    private Date executionDate;

    @Column(name = "dataUdostepnienia")
    private Date availabilityDate;

    @Column(name = "miejsceWykonania")
    private String performancePlace ;

    @Column(name = "charakterPracy")
    private String workNature;

    @Column(name = "godzinyPracy")
    private String workingHours;

    @Column(name = "stawkaGodzinowa")
    private Double hourlyRate ;

    @Column(name = "iloscMiejsc")
    private Integer vacanciesNumber;


    @ManyToOne(optional = false)
    @JoinColumn(name = "idPracodawcy")
    private Employer employer;

    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinTable(name="zlecenie_pracownik",
    joinColumns = @JoinColumn(name = "idZlecenia"), inverseJoinColumns = @JoinColumn(name = "idPracownika"))
    Set<Employee> employees;
}

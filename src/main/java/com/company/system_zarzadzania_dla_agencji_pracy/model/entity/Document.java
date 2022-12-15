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
@Table(name = "dokument")
public class Document {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer idDokumentu;

    @Column(name = "typDokumentu")
    private String documentType;

    @Column(name = "tresc")
    private String content;

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "idPracodawcy")
    private Employer employer;

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "idPracownika")
    private Employee employee;


}

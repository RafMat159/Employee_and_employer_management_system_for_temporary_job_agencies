package com.company.system_zarzadzania_dla_agencji_pracy.model.entity;


import com.company.system_zarzadzania_dla_agencji_pracy.model.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "uzytkownik")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idUzytkownika")
    private Integer idUzytkownika;

    @Column(name = "haslo")
    private String password;

    @Column(name = "mail")
    private String mail;

    @Column(name = "rola")
    @Enumerated(EnumType.STRING)
    private Role role;

}

package com.company.system_zarzadzania_dla_agencji_pracy;


import jakarta.persistence.*;
import org.springframework.lang.NonNull;

@Entity
@Table(name = "uzytkownik")
public class Uzytkownik {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer idUzytkownika;

    @NonNull
    private String haslo;

    @NonNull
    private String mail;

    @NonNull
    private String rola;

    @OneToOne(mappedBy = "uzytkownik")
    private Administrator administrator;

    @OneToOne(mappedBy = "uzytkownik")
    private PracownikAgencji pracownikAgencji;

    @OneToOne(mappedBy = "uzytkownik")
    private Pracownik pracownik;

    @OneToOne(mappedBy = "uzytkownik")
    private Pracodawca pracodawca;

}

package com.company.system_zarzadzania_dla_agencji_pracy.model.entity;


import org.springframework.lang.NonNull;

import javax.persistence.*;

@Entity
@Table(name = "uzytkownik")
public class User {

    //pola
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer idUzytkownika;

    @NonNull
    private String haslo;

    @NonNull
    private String mail;

    @NonNull
    private String rola;

    //relacje

    @OneToOne(mappedBy = "uzytkownik")
    private Administrator administrator;

    @OneToOne(mappedBy = "uzytkownik")
    private AgencyEmployee pracownikAgencji;

    @OneToOne(mappedBy = "uzytkownik")
    private Employee pracownik;

    @OneToOne(mappedBy = "uzytkownik")
    private Employer pracodawca;

    //konstruktory

    public User() {
    }

    public User(Integer idUzytkownika, @NonNull String haslo, @NonNull String mail, @NonNull String rola, Administrator administrator, AgencyEmployee pracownikAgencji, Employee pracownik, Employer pracodawca) {
        this.idUzytkownika = idUzytkownika;
        this.haslo = haslo;
        this.mail = mail;
        this.rola = rola;
        this.administrator = administrator;
        this.pracownikAgencji = pracownikAgencji;
        this.pracownik = pracownik;
        this.pracodawca = pracodawca;
    }


    //gettery i settery

    public Integer getIdUzytkownika() {
        return idUzytkownika;
    }

    public void setIdUzytkownika(Integer idUzytkownika) {
        this.idUzytkownika = idUzytkownika;
    }

    @NonNull
    public String getHaslo() {
        return haslo;
    }

    public void setHaslo(@NonNull String haslo) {
        this.haslo = haslo;
    }

    @NonNull
    public String getMail() {
        return mail;
    }

    public void setMail(@NonNull String mail) {
        this.mail = mail;
    }

    @NonNull
    public String getRola() {
        return rola;
    }

    public void setRola(@NonNull String rola) {
        this.rola = rola;
    }

    public Administrator getAdministrator() {
        return administrator;
    }

    public void setAdministrator(Administrator administrator) {
        this.administrator = administrator;
    }

    public AgencyEmployee getPracownikAgencji() {
        return pracownikAgencji;
    }

    public void setPracownikAgencji(AgencyEmployee pracownikAgencji) {
        this.pracownikAgencji = pracownikAgencji;
    }

    public Employee getPracownik() {
        return pracownik;
    }

    public void setPracownik(Employee pracownik) {
        this.pracownik = pracownik;
    }

    public Employer getPracodawca() {
        return pracodawca;
    }

    public void setPracodawca(Employer pracodawca) {
        this.pracodawca = pracodawca;
    }
}

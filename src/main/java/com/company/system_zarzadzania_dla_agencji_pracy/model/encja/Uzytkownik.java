package com.company.system_zarzadzania_dla_agencji_pracy.model.encja;


import org.springframework.lang.NonNull;

import javax.persistence.*;

@Entity
@Table(name = "uzytkownik")
public class Uzytkownik {

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
    private PracownikAgencji pracownikAgencji;

    @OneToOne(mappedBy = "uzytkownik")
    private Pracownik pracownik;

    @OneToOne(mappedBy = "uzytkownik")
    private Pracodawca pracodawca;

    //konstruktory

    public Uzytkownik() {
    }

    public Uzytkownik(Integer idUzytkownika, @NonNull String haslo, @NonNull String mail, @NonNull String rola, Administrator administrator, PracownikAgencji pracownikAgencji, Pracownik pracownik, Pracodawca pracodawca) {
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

    public PracownikAgencji getPracownikAgencji() {
        return pracownikAgencji;
    }

    public void setPracownikAgencji(PracownikAgencji pracownikAgencji) {
        this.pracownikAgencji = pracownikAgencji;
    }

    public Pracownik getPracownik() {
        return pracownik;
    }

    public void setPracownik(Pracownik pracownik) {
        this.pracownik = pracownik;
    }

    public Pracodawca getPracodawca() {
        return pracodawca;
    }

    public void setPracodawca(Pracodawca pracodawca) {
        this.pracodawca = pracodawca;
    }
}

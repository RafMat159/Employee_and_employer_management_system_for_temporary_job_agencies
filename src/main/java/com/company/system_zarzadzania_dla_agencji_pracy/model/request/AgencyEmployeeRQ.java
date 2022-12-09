package com.company.system_zarzadzania_dla_agencji_pracy.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.sql.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AgencyEmployeeRQ {

    @Email(message = "Nie podano poprawnego formatu mail. Nazwa domeny powinna zawierać @agencja.com", regexp = "^[A-Za-z0-9._%+-]+@agencja\\.com$")
    @NotBlank
    private String mail;

    @NotBlank(message = "Nie podano hasla")
    @Size(min = 2, message = "Podaj dluzsze haslo.")
    private String password;

    @NotBlank(message = "Nie podano imienia")
    @Size(min = 2, max = 18)
    private String name;

    @NotBlank(message = "Nie podano nazwiska")
    @Size(min = 2, message = "Nazwisko powinno miec wiecej niz dwa znaki")
    private String surname;

    @NotBlank(message = "Nie podano numeru telefonu.")
    private String phoneNumber;


    private String address;

    @NotBlank(message = "Nie podano poprawnego peselu")
    private String pesel;

    @NotNull
    @Past(message = "Nie podano daty z przeszlosci")
    private Date dateOfBirth;

}

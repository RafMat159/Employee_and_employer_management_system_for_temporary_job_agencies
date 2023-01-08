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
    @Size(min = 4, message = "Hasło powinno zawierać więcej niż 4 znaki")
    @Pattern(regexp = "^[A-Za-z0-9#%@!&]+$", message = "Hasło nie powinno zawierać białych znaków.Może zawierać znaki takie jak: A-Z, a-z, 0-9, #%@!&")
    private String password;

    @NotBlank(message = "Nie podano imienia")
    @Size(min = 2, max = 15, message = "Podane imie jest niepoprawne. Liczba znaków dla imienia powinna zawierać od 2 do 15 liter")
    @Pattern(regexp = "[A-Za-zżźćńółęąśŻŹĆĄŚĘŁÓŃ]+", message = "Imie użytkownika może zawierać tylko litery.")
    private String name;

    @NotBlank(message = "Nie podano nazwiska")
    @Size(min = 2, max = 15, message = "Podane nazwisko jest niepoprawne. Liczba znaków dla nazwiska powinna zawierać od 2 do 15 liter")
    @Pattern(regexp = "[A-Za-zżźćńółęąśŻŹĆĄŚĘŁÓŃ]+", message = "Nazwisko użytkownika może zawierać tylko litery.")
    private String surname;

    @NotBlank(message = "Nie podano numeru telefonu.")
    @Size(max = 15, message = "Numer telefonu powienien mieć 15 znaków")
    private String phoneNumber;

    @Size(max = 60, message = "Adres nie może przekraczać 60 znaków.")
    @Pattern(regexp = "^[^*&$#%^=<>!@\"]*$", message = "Podaj poprawny adres. Preferowany format adresu to: ul. nazwaulicy nr, kodpocztowy miasto")
    private String address;

    @NotBlank(message = "Nie podano poprawnego peselu")
    private String pesel;

    @NotNull
    @Past(message = "Nie podano daty z przeszlosci")
    private Date dateOfBirth;

}

package com.company.system_zarzadzania_dla_agencji_pracy.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployerRQ {

    @Email(message = "Nie podano poprawnego formatu mail. Nazwa domeny powinna zawierać @firma.com", regexp = "^[A-Za-z0-9._%+-]+@firma\\.com$")
    @NotBlank
    private String mail;

    @NotBlank(message = "Nie podano hasla")
    @Size(min = 2, message = "Podaj dluzsze haslo.")
    private String password;

    @NotBlank(message = "Nie podano nazwy firmy.")
    @Size(max=20, message = "Podana nazwa firmy jest za długa.")
    private String companyName;

    @Min(value = 4, message = "Podano niewlasciwy rok zalozenia")
    private Integer foundationYear;

    @NotBlank(message = "Nie podano adresu.")
    @Size(max = 60, message = "Adres nie może przekraczać 60 znaków")
    private String address;

    @NotBlank(message = "Nie podano numeru NIP.")
    private String NIP;

    @NotBlank(message = "Nie podanu numeru telefonu.")
    @Size(max = 15, message = "Numer telefonu powienien mieć 15 znaków")
    private String phoneNumber;


}

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
    @Size(min = 4, message = "Hasło powinno zawierać więcej niż 4 znaki")
    @Pattern(regexp = "^[A-Za-z0-9#%@!&]+$", message = "Hasło nie powinno zawierać białych znaków.Może zawierać znaki takie jak: A-Z, a-z, 0-9, #%@!&")
    private String password;

    @NotBlank(message = "Nie podano nazwy firmy.")
    @Size(min = 1, max = 20, message = "Podana nazwa firmy jest za długa.")
    private String companyName;

    @Min(value = 4, message = "Podano niewlasciwy rok zalozenia")
    private Integer foundationYear;

    @NotBlank(message = "Nie podano adresu.")
    @Size(min = 3, max = 60, message = "Adres nie może mieć mniej niż 3 znaki i nie może przekraczać 60 znaków.")
    @Pattern(regexp = "^[^*&$#%^=<>!@\"]+$", message = "Podaj poprawny adres. Preferowany format adresu to: ul. nazwaulicy nr, kodpocztowy miasto")
    private String address;

    @NotBlank(message = "Nie podano numeru NIP.")
    private String NIP;

    @NotBlank(message = "Nie podanu numeru telefonu.")
    @Size(max = 15, message = "Numer telefonu powienien mieć 15 znaków")
    private String phoneNumber;


}

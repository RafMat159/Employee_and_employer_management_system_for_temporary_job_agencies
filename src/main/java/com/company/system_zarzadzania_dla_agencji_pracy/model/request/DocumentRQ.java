package com.company.system_zarzadzania_dla_agencji_pracy.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DocumentRQ {

    @NotBlank(message = "Nie podano jakiego typu jest to dokument - TYTUŁ")
    @Size(min = 2, max = 100, message = "Typ dokumentu powinien być opisany w conajmniej dwóch znakach i nie przekraczać stu znaków.")
    @Pattern(regexp = "^[^<>*%:&/\\\\]+[A-Za-zżźćńółęąśŻŹĆĄŚĘŁÓŃ\s]+[0-9]*$", message = "Tytuł nie może zawierać takich znaków jak:<>*%:&/\\")
    private String documentType;

    @NotBlank(message = "Nie podano treści dokumentu")
    @Size(min = 4, message = "Podaj treść dokumentu, która przekracza 4 znaki.")
    private String content;

}

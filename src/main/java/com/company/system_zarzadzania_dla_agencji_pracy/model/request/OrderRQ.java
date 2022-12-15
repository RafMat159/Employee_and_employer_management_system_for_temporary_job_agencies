package com.company.system_zarzadzania_dla_agencji_pracy.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.sql.Date;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderRQ {

    @NotNull(message = "Nie podano daty wykonania zlecenia")
    @Future(message = "Podana data musi byc dniem w przyszlosci")
    private Date executionDate;

    @NotBlank(message = "Nie podano miejsca wykonania zlecenia")
    @Size(min=3,max=25,message = "Podaj poprawne miejsce wykonania zlecenia")
    private String performancePlace;

    @NotBlank(message = "Nie podano charakteru pracy")
    @Size(min=3,max=25,message = "Podaj poprawną nazwę charakteru pracy")
    private String workNature;

    @NotBlank(message = "Nie podano godzin pracy")
    @Pattern(regexp ="([01]?[0-9]|2[0-3]):[0-5][0-9]-([01]?[0-9]|2[0-3]):[0-5][0-9]",message = "Wymagany format dla godzin: hh:mm-hh:mm")
    private String workingHours;

    @NotNull(message = "Nie podano stawki godzinowej")
    @Positive(message = "Podana stawka godzinowa musi byc wieksza od 0")
    private Double hourlyRate;

    @NotNull(message = "Nie określono ilości miejsc")
    @Min(value = 1,message = "Podana liczba miejsc musi byc wieksza od 0" )
    private  Integer vacanciesNumber;


}
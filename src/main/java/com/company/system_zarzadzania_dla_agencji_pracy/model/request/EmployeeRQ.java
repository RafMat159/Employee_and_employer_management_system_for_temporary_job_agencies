package com.company.system_zarzadzania_dla_agencji_pracy.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeRQ {

    private String name;


    private String surname;


    private String phoneNumber;


    private String address;


    private String pesel;


    private boolean studentStatus;


    private Date dateOfBirth;
}

package org.isaci.bolsas_api.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.isaci.bolsas_api.enums.AcademicTitle;
import org.isaci.bolsas_api.enums.MaritalStatus;

import java.util.Date;

@Data
public class PersonDTO {

    @NotNull
    private String fullName;

    @NotNull
    private String cpf;

    @NotNull
    private String email;

    @NotNull
    private Date birthDate;

    @NotNull
    private String phone;

    @NotNull
    private MaritalStatus maritalStatus;

    @NotNull
    private String rg;

    @NotNull
    private String rgEmissor;

    @NotNull
    private AcademicTitle academicTitle;

    @NotNull
    private AddressDTO address;

    @NotNull
    private BankDataDTO bankData;
}

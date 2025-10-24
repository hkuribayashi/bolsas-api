package org.isaci.bolsas_api.dto.response;

import lombok.Data;
import org.isaci.bolsas_api.enums.AcademicTitle;
import org.isaci.bolsas_api.enums.MaritalStatus;

import java.util.Date;
import java.util.UUID;

@Data
public class PersonResponseDTO {

    private UUID id;
    private String fullName;
    private String cpf;
    private String email;
    private Date birthDate;
    private String phone;
    private MaritalStatus maritalStatus;
    private String rg;
    private String rgEmissor;
    private AcademicTitle academicTitle;
}

package org.isaci.bolsas_api.dto.response;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Data
public class ParticipationResponseDTO {

    private UUID id;
    private String role;
    private LocalDate startDate;
    private LocalDate endDate;
    private BigDecimal grantAmount;
    private String description;
    private Integer quantity;

    // Dados da pessoa
    private UUID personId;
    private String personName;
    private String personCpf;
    private String personEmail;

    // Dados do projeto
    private UUID projectId;
    private String projectName;
}

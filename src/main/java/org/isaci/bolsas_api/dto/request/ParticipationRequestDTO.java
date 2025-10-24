package org.isaci.bolsas_api.dto.request;

import brave.internal.Nullable;
import jakarta.validation.constraints.*;
import lombok.Data;
import org.isaci.bolsas_api.enums.ProjectRole;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Data
public class ParticipationRequestDTO {

    @NotNull(message = "O ID da pessoa é obrigatório")
    private UUID personId;

    @NotNull(message = "O ID do projeto é obrigatório")
    private UUID projectId;

    @NotNull(message = "O papel no projeto é obrigatório")
    private ProjectRole role;

    @NotNull(message = "A data de início é obrigatória")
    @PastOrPresent(message = "A data de início não pode ser futura")
    private LocalDate startDate;

    @Nullable
    private LocalDate endDate;

    @DecimalMin(value = "0.0", inclusive = false, message = "O valor da bolsa deve ser positivo")
    @Digits(integer = 10, fraction = 2, message = "O valor da bolsa deve ter no máximo 10 dígitos e 2 casas decimais")
    private BigDecimal grantAmount;

    @NotNull
    private String description;

    @NotNull
    private Integer quantity;
}

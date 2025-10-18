package org.isaci.bolsas_api.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.isaci.bolsas_api.enums.PixKey;

@Data
public class BankDataDTO {

    @NotNull
    private String bankName;

    @NotBlank
    @Size(max = 5, message = "O número da agência deve ter no máximo 5 caracteres")
    private String agencyNumber;

    @NotBlank
    @Size(min = 1, max = 1, message = "O dígito verificador deve conter exatamente 1 caractere")
    private String agencyDV;

    @NotNull
    @NotBlank
    @Size(min = 1, max = 1, message = "O dígito verificador deve conter exatamente 1 caractere")
    private String accountNumber;

    @NotBlank
    @Size(min = 1, max = 1, message = "O dígito verificador deve conter exatamente 1 caractere")
    private String accountDV;

    @NotNull
    private String pixKey;

    @NotNull
    private PixKey pixKeyType;
}

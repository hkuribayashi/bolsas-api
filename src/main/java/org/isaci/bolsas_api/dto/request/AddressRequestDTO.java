package org.isaci.bolsas_api.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AddressRequestDTO {

    @NotNull
    private String street;

    @NotNull
    private String city;

    @NotNull
    private String state;

    @NotNull
    private String zip;

    @NotNull
    private String country;

    @NotNull
    private Integer number;

    private String complement;

    private String neighborhood;
}

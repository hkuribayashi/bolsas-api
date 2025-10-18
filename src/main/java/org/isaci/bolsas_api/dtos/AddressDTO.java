package org.isaci.bolsas_api.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AddressDTO {

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
}

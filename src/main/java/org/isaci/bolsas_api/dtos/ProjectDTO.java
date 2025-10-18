package org.isaci.bolsas_api.dtos;

import lombok.Data;
import jakarta.validation.constraints.NotNull;

import java.util.Date;

@Data
public class ProjectDTO {

    @NotNull
    private String name;

    @NotNull
    private String description;

    @NotNull
    private Date startDate;

    @NotNull
    private Date endDate;

}

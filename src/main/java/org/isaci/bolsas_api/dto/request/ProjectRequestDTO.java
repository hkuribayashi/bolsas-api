package org.isaci.bolsas_api.dto.request;

import lombok.Data;
import jakarta.validation.constraints.NotNull;

import java.util.Date;

@Data
public class ProjectRequestDTO {

    @NotNull
    private String name;

    @NotNull
    private String description;

    @NotNull
    private Date startDate;

    private Date endDate;

}

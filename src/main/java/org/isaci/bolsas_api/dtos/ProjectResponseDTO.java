package org.isaci.bolsas_api.dtos;

import lombok.Data;
import java.util.Date;
import java.util.UUID;

@Data
public class ProjectResponseDTO {
    private UUID id;
    private String name;
    private String description;
    private Date startDate;
    private Date endDate;
    private Boolean isActive;
}

package org.isaci.bolsas_api.model;

import jakarta.persistence.*;
import lombok.Data;
import org.isaci.bolsas_api.enums.ProjectRole;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Data
public class ParticipationModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "person_id", nullable = false)
    private PersonModel person;

    @ManyToOne(optional = false)
    @JoinColumn(name = "project_id", nullable = false)
    private ProjectModel project;

    @Column(nullable = false)
    private LocalDate startDate;

    @Column(nullable = false)
    private LocalDate endDate;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ProjectRole role;

    @Column(precision = 10, scale = 2)
    private BigDecimal grantAmount;

    @Column(nullable = false)
    private Integer quantity;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;
}

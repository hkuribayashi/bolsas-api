package org.isaci.bolsas_api.model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Data
public class GrantPaymentModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "person_id")
    private PersonModel recipient;

    @ManyToOne(optional = false)
    @JoinColumn(name = "project_id")
    private ProjectModel project;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal amount;

    @Column(nullable = false)
    private Integer quantity;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;
}

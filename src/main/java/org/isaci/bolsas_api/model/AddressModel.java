package org.isaci.bolsas_api.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Entity
@Data
public class AddressModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false)
    private String street;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private String state;

    @Column(nullable = false)
    private String zip;

    @Column(nullable = false)
    private String country;

    @Column(nullable = false)
    private Integer number;

    @Column
    private String complement;

    @Column(nullable = false)
    private String neighborhood;

    @OneToOne(mappedBy = "address", optional = false)
    @JsonBackReference
    private PersonModel person;
}
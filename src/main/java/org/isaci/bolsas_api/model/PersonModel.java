package org.isaci.bolsas_api.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import lombok.Data;
import org.isaci.bolsas_api.enums.AcademicTitle;
import org.isaci.bolsas_api.enums.MaritalStatus;

@Entity
@Data
public class PersonModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false)
    private String fullName;

    @Column(nullable = false, unique = true)
    private String cpf;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private Date birthDate;

    @Column(nullable = false)
    private String phone;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private MaritalStatus maritalStatus;

    @Column(nullable = false)
    private String rg;

    @Column(nullable = false)
    private String rgEmissor;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id")
    @JsonManagedReference
    private AddressModel address;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "bank_id")
    @JsonManagedReference
    private BankDataModel bankData;

    @OneToMany(mappedBy = "person")
    private List<ParticipationModel> participations;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AcademicTitle academicTitle;

}

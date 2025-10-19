package org.isaci.bolsas_api.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import org.isaci.bolsas_api.enums.PixKey;

import java.util.UUID;

@Entity
@Data
public class BankDataModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String bankName;

    @Column(length = 4, nullable = false)
    private String agencyNumber;

    @Column(length = 1, nullable = true)
    private String agencyDV;

    @Column(nullable = false)
    private String accountNumber;

    @Column(length = 1, nullable = false)
    private String accountDV;

    @Column(nullable = false)
    private String pixKey;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PixKey pixKeyType;

    @OneToOne(mappedBy = "bankData", optional = false)
    @JsonBackReference
    private PersonModel person;

}

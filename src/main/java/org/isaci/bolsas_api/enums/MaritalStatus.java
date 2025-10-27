package org.isaci.bolsas_api.enums;


public enum MaritalStatus {

    SINGLE("Solteiro"),
    MARRIED("Casado"),
    SEPARATED("Separado"),
    DIVORCED("Divorciado"),
    WIDOWED("Viúvo"),
    COMMON_LAW_MARRIAGE("União Estável");

    private final String description;

    MaritalStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
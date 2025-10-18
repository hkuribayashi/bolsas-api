package org.isaci.bolsas_api.enums;


public enum MaritalStatus {

    SINGLE("Single"),
    MARRIED("Married"),
    SEPARATED("Separated"),
    DIVORCED("Divorced"),
    WIDOWED("Widowed"),
    COMMON_LAW_MARRIAGE("Common-law Marriage");

    private final String description;

    MaritalStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
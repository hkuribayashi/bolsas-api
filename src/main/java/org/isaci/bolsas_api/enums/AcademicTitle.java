package org.isaci.bolsas_api.enums;

public enum AcademicTitle {

    HIGH_SCHOOL("Ensino Médio"),
    TECHNICIAN("Técnico"),
    UNDERGRADUATE("Graduação"),
    SPECIALIST("Especialista"),
    MASTER("Mestre"),
    DOCTOR("Doutor"),
    POST_DOCTOR("Pós-Doutor"),
    PROFESSOR("Professor");

    private final String description;

    AcademicTitle(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}

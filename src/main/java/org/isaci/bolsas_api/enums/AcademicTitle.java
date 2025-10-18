package org.isaci.bolsas_api.enums;

public enum AcademicTitle {

    HIGH_SCHOOL("High School"),
    TECHNICIAN("Technician"),
    UNDERGRADUATE("Undergraduate"),
    BACHELOR("Bachelor"),
    SPECIALIST("Specialist"),
    MASTER("Master"),
    DOCTOR("Doctor"),
    POST_DOCTOR("Postdoctoral"),
    PROFESSOR("Professor");

    private final String description;

    AcademicTitle(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
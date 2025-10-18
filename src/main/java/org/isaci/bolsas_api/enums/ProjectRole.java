package org.isaci.bolsas_api.enums;

public enum ProjectRole {

    COORDINATOR("Coordinator"),
    FELLOW("Fellow"),
    COLLABORATOR("Collaborator"),
    ADVISOR("Advisor"),
    ASSISTANT("Assistant");

    private final String description;

    ProjectRole(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}

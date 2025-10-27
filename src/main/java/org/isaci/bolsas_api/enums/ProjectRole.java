package org.isaci.bolsas_api.enums;

public enum ProjectRole {

    COORDINATOR("Coordenador"),
    COLLABORATOR("Colaborador"),
    ADVISOR("Orientador"),
    RESEARCHER("Pesquisador");

    private final String description;

    ProjectRole(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}

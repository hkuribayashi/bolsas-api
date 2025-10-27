package org.isaci.bolsas_api.enums;

public enum PixKey {

    TELEPHONE("telefone"),
    EMAIL("email"),
    CPF_CNPJ("cpf_cnpj"),
    RANDOM("aleatória");

    private final String description;

    PixKey(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}

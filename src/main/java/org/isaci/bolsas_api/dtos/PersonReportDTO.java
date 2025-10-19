package org.isaci.bolsas_api.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PersonReportDTO {
    private String nome;
    private String cpf;
    private String rg;
    private String emissorRG;
    private Date dataNascimento;
    private String estadoCivil;
    private String naturalidade;
    private String grauInstrucao;
    private String telefone;
    private String email;
    private String logradouro;
    private String numero;
    private String complemento;
    private String bairro;
    private String cidade;
    private String estado;
    private String cep;
    private String banco;
    private String agencia;
    private String dvagencia;
    private String conta;
    private String dvconta;
    private BigDecimal valorBolsa;
    private Integer quantidade;
    private BigDecimal totalPagamentos;
    private String atividades;
    private String nomeprojeto;
}

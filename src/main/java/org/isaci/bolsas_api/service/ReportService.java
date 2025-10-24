package org.isaci.bolsas_api.service;

import lombok.RequiredArgsConstructor;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.isaci.bolsas_api.model.ParticipationModel;
import org.isaci.bolsas_api.model.PersonModel;
import org.isaci.bolsas_api.repository.ParticipationRepository;
import org.isaci.bolsas_api.repository.PersonRepository;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReportService {

    private final PersonRepository personRepository;
    private final ParticipationRepository participationRepository;

    /**
     * Gera relatório PDF de uma pessoa num projeto específico.
     */
    public byte[] generatePersonProjectReport(UUID projectId, UUID personId) throws Exception {

        // 1️⃣ Buscar pessoa
        PersonModel person = personRepository.findById(personId)
                .orElseThrow(() -> new IllegalArgumentException("Pessoa não encontrada com o ID: " + personId));

        // 2️⃣ Buscar participações dessa pessoa no projeto informado
        List<ParticipationModel> participations = participationRepository
                .findByPersonIdAndProjectId(personId, projectId);

        if (participations.isEmpty()) {
            throw new IllegalArgumentException("Nenhuma participação encontrada para esta pessoa neste projeto.");
        }

        // 3️⃣ Montar dataset (um registry por participação)
        List<Map<String, Object>> data = participations.stream().map(p -> {
            Map<String, Object> map = new HashMap<>();

            // --- Dados pessoais ---
            map.put("nome", person.getFullName());
            map.put("cpf", person.getCpf());
            map.put("rg", person.getRg());
            map.put("emissorRG", person.getRgEmissor());
            map.put("dataNascimento", person.getBirthDate());
            map.put("estadoCivil", person.getMaritalStatus().name());
            map.put("telefone", person.getPhone());
            map.put("email", person.getEmail());
            map.put("grauInstrucao", person.getAcademicTitle().getDescription());

            // --- Endereço ---
            if (person.getAddress() != null) {
                map.put("logradouro", person.getAddress().getStreet());
                map.put("numero", person.getAddress().getNumber());
                map.put("complemento", person.getAddress().getComplement());
                map.put("bairro", person.getAddress().getNeighborhood());
                map.put("cidade", person.getAddress().getCity());
                map.put("estado", person.getAddress().getState());
                map.put("cep", person.getAddress().getZip());
            }

            // --- Dados bancários ---
            if (person.getBankData() != null) {
                map.put("banco", person.getBankData().getBankName());
                map.put("agencia", person.getBankData().getAgencyNumber());
                map.put("dvagencia", person.getBankData().getAgencyDV());
                map.put("conta", person.getBankData().getAccountNumber());
                map.put("dvconta", person.getBankData().getAccountDV());
            }

            // --- Dados da participação no projeto ---
            BigDecimal valorMensal = p.getGrantAmount() != null ? p.getGrantAmount() : BigDecimal.ZERO;
            int qtdParcelas = p.getQuantity() != null ? p.getQuantity() : 0;
            BigDecimal valorTotal = valorMensal.multiply(BigDecimal.valueOf(qtdParcelas));

            map.put("valorBolsa", valorMensal);
            map.put("quantidade", qtdParcelas);
            map.put("totalPagamentos", valorTotal);
            map.put("atividades", p.getDescription());
            map.put("nomeprojeto", p.getProject().getName());

            return map;
        }).collect(Collectors.toList());

        InputStream reportStream = new ClassPathResource("reports/isaci_formulario_bolsa.jrxml").getInputStream();
        JasperReport jasperReport = JasperCompileManager.compileReport(reportStream);

        Map<String, Object> params = new HashMap<>();
        File logoFile = new ClassPathResource("static/logo.png").getFile();
        params.put("LOGO_PATH", logoFile.getAbsolutePath());

        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(data);
        JasperPrint print = JasperFillManager.fillReport(jasperReport, params, dataSource);

        return JasperExportManager.exportReportToPdf(print);
    }
}
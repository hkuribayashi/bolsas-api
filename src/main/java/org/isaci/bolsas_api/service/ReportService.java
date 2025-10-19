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
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReportService {

    private final PersonRepository personRepository;
    private final ParticipationRepository participationRepository;

    /**
     * Gera o relatório PDF de uma pessoa e suas participações.
     */
    public byte[] generatePersonReport(UUID personId) throws Exception {
        // 🔍 1. Busca a pessoa
        PersonModel person = personRepository.findById(personId)
                .orElseThrow(() -> new IllegalArgumentException("Person not found with id: " + personId));

        // 🔍 2. Busca participações dessa pessoa
        List<ParticipationModel> participations = participationRepository.findByPersonId(personId);

        // 🔍 3. Prepara o dataset para o relatório
        List<Map<String, Object>> data = participations.stream().map(p -> {
            Map<String, Object> map = new HashMap<>();
            map.put("nome", person.getFullName());
            map.put("cpf", person.getCpf());
            map.put("rg", person.getRg());
            map.put("emissorRG", person.getRgEmissor());
            map.put("dataNascimento", person.getBirthDate());
            map.put("estadoCivil", person.getMaritalStatus().name());
            map.put("telefone", person.getPhone());
            map.put("email", person.getEmail());
            map.put("grauInstrucao", person.getAcademicTitle().name());
            map.put("logradouro", person.getAddress().getStreet());
            map.put("numero", person.getAddress().getNumber());
            map.put("complemento", person.getAddress().getComplement());
            map.put("bairro", person.getAddress().getCity());
            map.put("cidade", person.getAddress().getCity());
            map.put("estado", person.getAddress().getState());
            map.put("cep", person.getAddress().getZip());
            map.put("banco", person.getBankData().getBankName());
            map.put("agencia", person.getBankData().getAgencyNumber());
            map.put("dvagencia", person.getBankData().getAgencyDV());
            map.put("conta", person.getBankData().getAccountNumber());
            map.put("dvconta", person.getBankData().getAccountDV());
            map.put("valorBolsa", p.getGrantAmount());
            map.put("quantidade", p.getQuantity());
            map.put("totalPagamentos", p.getGrantAmount());
            map.put("atividades", p.getDescription());
            map.put("nomeprojeto", p.getProject().getName());
            return map;
        }).collect(Collectors.toList());

        // 🔸 4. Carrega o arquivo JRXML
        InputStream reportStream = new ClassPathResource("reports/isaci_formulario_bolsa.jrxml").getInputStream();
        JasperReport jasperReport = JasperCompileManager.compileReport(reportStream);

        // 🔸 5. Prepara parâmetros
        Map<String, Object> params = new HashMap<>();
        File logoFile = new ClassPathResource("static/logo.png").getFile();
        params.put("LOGO_PATH", logoFile.getAbsolutePath());

        // 🔸 6. Gera o PDF
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(data);
        JasperPrint print = JasperFillManager.fillReport(jasperReport, params, dataSource);

        return JasperExportManager.exportReportToPdf(print);
    }
}

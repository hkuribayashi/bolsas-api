package org.isaci.bolsas_api.report;

import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperReport;
import org.junit.jupiter.api.Test;

import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.*;

public class JasperReportTest {

    @Test
    void deveCarregarECompilarRelatorio() throws Exception {
        // Caminho relativo dentro de src/main/resources/
        String reportPath = "/reports/isaci_formulario_bolsa.jrxml";

        // Tenta carregar o arquivo JRXML
        InputStream reportStream = getClass().getResourceAsStream(reportPath);
        assertNotNull(reportStream, "❌ O arquivo de relatório não foi encontrado no classpath: " + reportPath);

        // Compila o relatório (testa sintaxe e referências)
        JasperReport report = JasperCompileManager.compileReport(reportStream);
        assertNotNull(report, "❌ O relatório não pôde ser compilado.");

        System.out.println("✅ Relatório compilado com sucesso: " + report.getName());
    }
}

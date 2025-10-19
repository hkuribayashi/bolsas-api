package org.isaci.bolsas_api.controller;

import lombok.RequiredArgsConstructor;
import org.isaci.bolsas_api.service.ReportService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/admin/reports")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;

    @GetMapping("/person/{personId}")
    public ResponseEntity<byte[]> generatePersonReport(@PathVariable UUID personId) {
        try {
            byte[] pdf = reportService.generatePersonReport(personId);

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=relatorio_bolsista.pdf")
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(pdf);
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .contentType(MediaType.TEXT_PLAIN)
                    .body(("Erro ao gerar relatório: " + e.getMessage()).getBytes());
        }
    }
}

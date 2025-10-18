package org.isaci.bolsas_api.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.isaci.bolsas_api.dtos.ParticipationDTO;
import org.isaci.bolsas_api.model.ParticipationModel;
import org.isaci.bolsas_api.service.ParticipationService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/admin/participations")
@RequiredArgsConstructor
public class ParticipationController {

    private final ParticipationService participationService;

    /**
     * Cria uma participação.
     */
    @PostMapping
    public ResponseEntity<ParticipationModel> createParticipation(@RequestBody @Valid ParticipationDTO participationDTO) {
        ParticipationModel savedParticipation = participationService.save(participationDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedParticipation);
    }

    /**
     * Retorna uma participação pelo ‘ID’.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ParticipationModel> getParticipationById(@PathVariable UUID id) {
        ParticipationModel participation = participationService.findById(id);
        return ResponseEntity.ok(participation);
    }

    /**
     * Lista todas as participações paginadas.
     */
    @GetMapping
    public ResponseEntity<Page<ParticipationModel>> getAllParticipations(
            @PageableDefault(sort = "startDate", direction = Sort.Direction.ASC) Pageable pageable) {
        Page<ParticipationModel> page = participationService.findAll(pageable);
        return ResponseEntity.ok(page);
    }

    /**
     * Atualiza uma participação existente.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ParticipationModel> updateParticipation(@PathVariable UUID id,
                                                             @RequestBody @Valid ParticipationDTO participationDTO) {
        ParticipationModel updatedParticipation = participationService.update(id, participationDTO);
        return ResponseEntity.ok(updatedParticipation);
    }

    /**
     * Exclui uma participação pelo ‘ID’.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteParticipation(@PathVariable UUID id) {
        participationService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

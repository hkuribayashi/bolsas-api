package org.isaci.bolsas_api.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.isaci.bolsas_api.dto.request.ParticipationRequestDTO;
import org.isaci.bolsas_api.dto.response.ParticipationResponseDTO;
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

    @PostMapping
    public ResponseEntity<ParticipationResponseDTO> create(@RequestBody @Valid ParticipationRequestDTO dto) {
        ParticipationResponseDTO created = participationService.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ParticipationResponseDTO> update(@PathVariable UUID id, @RequestBody @Valid ParticipationRequestDTO dto) {
        ParticipationResponseDTO updated = participationService.update(id, dto);
        return ResponseEntity.ok(updated);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ParticipationResponseDTO> getById(@PathVariable UUID id) {
        ParticipationResponseDTO found = participationService.findById(id);
        return ResponseEntity.ok(found);
    }

    @GetMapping
    public ResponseEntity<Page<ParticipationResponseDTO>> getAll(
            @PageableDefault(sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
        Page<ParticipationResponseDTO> participations = participationService.findAll(pageable);
        return ResponseEntity.ok(participations);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        participationService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

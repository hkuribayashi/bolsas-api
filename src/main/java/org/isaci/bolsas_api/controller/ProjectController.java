package org.isaci.bolsas_api.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.isaci.bolsas_api.dtos.ProjectDTO;
import org.isaci.bolsas_api.dtos.ProjectResponseDTO;
import org.isaci.bolsas_api.service.ProjectService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/admin/projects")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;

    /**
     * Cria um novo projeto.
     */
    @PostMapping
    public ResponseEntity<ProjectResponseDTO> createProject(@RequestBody @Valid ProjectDTO projectDTO) {
        ProjectResponseDTO saved = projectService.save(projectDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    /**
     * Retorna um projeto pelo ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ProjectResponseDTO> getProjectById(@PathVariable UUID id) {
        ProjectResponseDTO project = projectService.findById(id);
        return ResponseEntity.ok(project);
    }

    /**
     * Lista todos os projetos paginados com JSON estável.
     */
    @GetMapping
    public ResponseEntity<Page<ProjectResponseDTO>> getAllProjects(
            @PageableDefault(size = 5, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
        Page<ProjectResponseDTO> page = projectService.findAll(pageable);
        return ResponseEntity.ok(page);
    }

    /**
     * Atualiza um projeto existente.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ProjectResponseDTO> updateProject(@PathVariable UUID id,
                                                            @RequestBody @Valid ProjectDTO projectDTO) {
        ProjectResponseDTO updated = projectService.update(id, projectDTO);
        return ResponseEntity.ok(updated);
    }

    /**
     * Exclui um projeto pelo ID.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProject(@PathVariable UUID id) {
        projectService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
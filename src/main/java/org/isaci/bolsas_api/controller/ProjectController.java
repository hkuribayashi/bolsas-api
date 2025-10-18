package org.isaci.bolsas_api.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.isaci.bolsas_api.dtos.ProjectDTO;
import org.isaci.bolsas_api.model.ProjectModel;
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
    public ResponseEntity<ProjectModel> createProject(@RequestBody @Valid ProjectDTO projectDTO) {
        ProjectModel savedProject = projectService.save(projectDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedProject);
    }

    /**
     * Retorna um projeto pelo ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ProjectModel> getProjectById(@PathVariable UUID id) {
        ProjectModel project = projectService.findById(id);
        return ResponseEntity.ok(project);
    }

    /**
     * Lista todos os projetos paginados.
     */
    @GetMapping
    public ResponseEntity<Page<ProjectModel>> getAllProjects(
            @PageableDefault(size = 5, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
        Page<ProjectModel> page = projectService.findAll(pageable);
        return ResponseEntity.ok(page);
    }

    /**
     * Atualiza um projeto existente.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ProjectModel> updateProject(@PathVariable UUID id,
                                                      @RequestBody @Valid ProjectDTO projectDTO) {
        ProjectModel updatedProject = projectService.update(id, projectDTO);
        return ResponseEntity.ok(updatedProject);
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

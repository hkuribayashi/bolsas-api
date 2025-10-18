package org.isaci.bolsas_api.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.isaci.bolsas_api.dtos.ProjectDTO;
import org.isaci.bolsas_api.exceptions.ResourceNotFoundException;
import org.isaci.bolsas_api.model.ProjectModel;
import org.isaci.bolsas_api.repository.ProjectRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final ModelMapper modelMapper;

    /**
     * Cria e salva um novo projeto.
     */
    public ProjectModel save(ProjectDTO projectDTO) {
        ProjectModel project = modelMapper.map(projectDTO, ProjectModel.class);
        return projectRepository.save(project);
    }

    /**
     * Atualiza um projeto existente pelo ID.
     * Lança exceção se o projeto não for encontrado.
     */
    @Transactional
    public ProjectModel update(UUID id, ProjectDTO projectDTO) {
        ProjectModel existingProject = projectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Project not found with id: " + id));

        // Atualiza apenas os campos modificáveis
        modelMapper.map(projectDTO, existingProject);

        return projectRepository.save(existingProject);
    }

    /**
     * Busca um projeto pelo ID.
     * Lança exceção se não encontrado.
     */
    public ProjectModel findById(UUID id) {
        return projectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Project not found with id: " + id));
    }

    /**
     * Retorna uma página de projetos.
     */
    public Page<ProjectModel> findAll(Pageable pageable) {
        return projectRepository.findAll(pageable);
    }

    /**
     * Exclui um projeto pelo ID.
     */
    @Transactional
    public void deleteById(UUID id) {
        ProjectModel project = projectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Project not found with id: " + id));
        projectRepository.delete(project);
    }
}

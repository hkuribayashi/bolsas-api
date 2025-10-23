package org.isaci.bolsas_api.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.isaci.bolsas_api.dtos.ProjectDTO;
import org.isaci.bolsas_api.dtos.ProjectResponseDTO;
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
    public ProjectResponseDTO save(ProjectDTO projectDTO) {
        ProjectModel project = modelMapper.map(projectDTO, ProjectModel.class);
        ProjectModel saved = projectRepository.save(project);
        return modelMapper.map(saved, ProjectResponseDTO.class);
    }

    /**
     * Atualiza um projeto existente pelo ID.
     */
    @Transactional
    public ProjectResponseDTO update(UUID id, ProjectDTO projectDTO) {
        ProjectModel existing = projectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Project not found with id: " + id));

        modelMapper.map(projectDTO, existing);
        ProjectModel updated = projectRepository.save(existing);

        return modelMapper.map(updated, ProjectResponseDTO.class);
    }

    /**
     * Busca um projeto pelo ID e retorna como DTO.
     */
    public ProjectResponseDTO findById(UUID id) {
        ProjectModel project = projectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Project not found with id: " + id));
        return modelMapper.map(project, ProjectResponseDTO.class);
    }

    /**
     * Retorna uma página de projetos convertidos em DTO.
     */
    public Page<ProjectResponseDTO> findAll(Pageable pageable) {
        return projectRepository.findAll(pageable)
                .map(project -> modelMapper.map(project, ProjectResponseDTO.class));
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

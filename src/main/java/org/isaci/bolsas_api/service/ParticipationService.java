package org.isaci.bolsas_api.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.isaci.bolsas_api.dtos.ParticipationDTO;
import org.isaci.bolsas_api.exceptions.ResourceNotFoundException;
import org.isaci.bolsas_api.model.ParticipationModel;
import org.isaci.bolsas_api.model.PersonModel;
import org.isaci.bolsas_api.model.ProjectModel;
import org.isaci.bolsas_api.repository.ParticipationRepository;
import org.isaci.bolsas_api.repository.PersonRepository;
import org.isaci.bolsas_api.repository.ProjectRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ParticipationService {

    private final ParticipationRepository participationRepository;
    private final PersonRepository personRepository;
    private final ProjectRepository projectRepository;
    private final ModelMapper modelMapper;

    /**
     * Cria e salva uma nova participação.
     */
    @Transactional
    public ParticipationModel save(ParticipationDTO participationDTO) {
        PersonModel person = personRepository.findById(participationDTO.getPersonId())
                .orElseThrow(() -> new ResourceNotFoundException("Person not found with id: " + participationDTO.getPersonId()));

        ProjectModel project = projectRepository.findById(participationDTO.getProjectId())
                .orElseThrow(() -> new ResourceNotFoundException("Project not found with id: " + participationDTO.getProjectId()));

        ParticipationModel participation = modelMapper.map(participationDTO, ParticipationModel.class);
        participation.setPerson(person);
        participation.setProject(project);

        return participationRepository.save(participation);
    }

    /**
     * Atualiza uma participação existente.
     */
    @Transactional
    public ParticipationModel update(UUID id, ParticipationDTO participationDTO) {
        ParticipationModel existing = participationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Participation not found with id: " + id));

        // Atualiza dados principais
        modelMapper.map(participationDTO, existing);

        // Atualiza relacionamentos
        if (participationDTO.getPersonId() != null) {
            PersonModel person = personRepository.findById(participationDTO.getPersonId())
                    .orElseThrow(() -> new ResourceNotFoundException("Person not found with id: " + participationDTO.getPersonId()));
            existing.setPerson(person);
        }

        if (participationDTO.getProjectId() != null) {
            ProjectModel project = projectRepository.findById(participationDTO.getProjectId())
                    .orElseThrow(() -> new ResourceNotFoundException("Project not found with id: " + participationDTO.getProjectId()));
            existing.setProject(project);
        }

        return participationRepository.save(existing);
    }

    /**
     * Busca uma participação pelo ‘ID’.
     */
    public ParticipationModel findById(UUID id) {
        return participationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Participation not found with id: " + id));
    }

    /**
     * Retorna uma lista paginada de participações.
     */
    public Page<ParticipationModel> findAll(Pageable pageable) {
        return participationRepository.findAll(pageable);
    }

    /**
     * Exclui uma participação pelo ‘ID’.
     */
    @Transactional
    public void deleteById(UUID id) {
        ParticipationModel participation = participationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Participation not found with id: " + id));
        participationRepository.delete(participation);
    }
}

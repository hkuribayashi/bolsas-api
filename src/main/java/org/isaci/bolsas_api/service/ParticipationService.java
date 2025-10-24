package org.isaci.bolsas_api.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.isaci.bolsas_api.dto.request.ParticipationRequestDTO;
import org.isaci.bolsas_api.dto.response.ParticipationResponseDTO;
import org.isaci.bolsas_api.exceptions.ResourceNotFoundException;
import org.isaci.bolsas_api.model.ParticipationModel;
import org.isaci.bolsas_api.model.PersonModel;
import org.isaci.bolsas_api.model.ProjectModel;
import org.isaci.bolsas_api.repository.ParticipationRepository;
import org.isaci.bolsas_api.repository.PersonRepository;
import org.isaci.bolsas_api.repository.ProjectRepository;
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

    /**
     * Cria uma participação.
     */
    @Transactional
    public ParticipationResponseDTO save(ParticipationRequestDTO dto) {
        PersonModel person = personRepository.findById(dto.getPersonId())
                .orElseThrow(() -> new ResourceNotFoundException("Person not found with id: " + dto.getPersonId()));

        ProjectModel project = projectRepository.findById(dto.getProjectId())
                .orElseThrow(() -> new ResourceNotFoundException("Project not found with id: " + dto.getProjectId()));

        ParticipationModel participation = new ParticipationModel();
        participation.setPerson(person);
        participation.setProject(project);
        participation.setRole(dto.getRole());
        participation.setStartDate(dto.getStartDate());
        participation.setEndDate(dto.getEndDate());
        participation.setGrantAmount(dto.getGrantAmount());
        participation.setDescription(dto.getDescription());
        participation.setQuantity(dto.getQuantity());

        ParticipationModel saved = participationRepository.save(participation);
        return mapToResponse(saved);
    }

    /**
     * Atualiza uma participação existente.
     */
    @Transactional
    public ParticipationResponseDTO update(UUID id, ParticipationRequestDTO dto) {
        ParticipationModel existing = participationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Participation not found with id: " + id));

        PersonModel person = personRepository.findById(dto.getPersonId())
                .orElseThrow(() -> new ResourceNotFoundException("Person not found with id: " + dto.getPersonId()));

        ProjectModel project = projectRepository.findById(dto.getProjectId())
                .orElseThrow(() -> new ResourceNotFoundException("Project not found with id: " + dto.getProjectId()));

        existing.setPerson(person);
        existing.setProject(project);
        existing.setRole(dto.getRole());
        existing.setStartDate(dto.getStartDate());
        existing.setEndDate(dto.getEndDate());
        existing.setGrantAmount(dto.getGrantAmount());
        existing.setDescription(dto.getDescription());
        existing.setQuantity(dto.getQuantity());

        ParticipationModel updated = participationRepository.save(existing);
        return mapToResponse(updated);
    }

    /**
     * Busca uma participação pelo ‘ID’.
     */
    public ParticipationResponseDTO findById(UUID id) {
        ParticipationModel model = participationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Participation not found with id: " + id));
        return mapToResponse(model);
    }

    /**
     * Lista todas as participações com paginação.
     */
    public Page<ParticipationResponseDTO> findAll(Pageable pageable) {
        return participationRepository.findAll(pageable).map(this::mapToResponse);
    }

    /**
     * Exclui uma participação.
     */
    @Transactional
    public void deleteById(UUID id) {
        ParticipationModel existing = participationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Participation not found with id: " + id));
        participationRepository.delete(existing);
    }

    /**
     * Converte a entidade para DTO de resposta.
     */
    private ParticipationResponseDTO mapToResponse(ParticipationModel participation) {
        ParticipationResponseDTO dto = new ParticipationResponseDTO();
        dto.setId(participation.getId());
        dto.setRole(participation.getRole().getDescription());
        dto.setStartDate(participation.getStartDate());
        dto.setEndDate(participation.getEndDate());
        dto.setGrantAmount(participation.getGrantAmount());
        dto.setDescription(participation.getDescription());
        dto.setQuantity(participation.getQuantity());

        dto.setPersonId(participation.getPerson().getId());
        dto.setPersonName(participation.getPerson().getFullName());
        dto.setPersonCpf(participation.getPerson().getCpf());
        dto.setPersonEmail(participation.getPerson().getEmail());

        dto.setProjectId(participation.getProject().getId());
        dto.setProjectName(participation.getProject().getName());

        return dto;
    }
}

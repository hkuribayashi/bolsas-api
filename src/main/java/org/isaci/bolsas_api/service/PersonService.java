package org.isaci.bolsas_api.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.isaci.bolsas_api.dto.request.PersonRequestDTO;
import org.isaci.bolsas_api.dto.response.PersonResponseDTO;
import org.isaci.bolsas_api.exceptions.ResourceNotFoundException;
import org.isaci.bolsas_api.model.AddressModel;
import org.isaci.bolsas_api.model.BankDataModel;
import org.isaci.bolsas_api.model.PersonModel;
import org.isaci.bolsas_api.repository.PersonRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PersonService {

    private final PersonRepository personRepository;
    private final ModelMapper modelMapper;

    /**
     * Cria e salva uma nova pessoa com endereço e dados bancários.
     */
    @Transactional
    public PersonResponseDTO save(PersonRequestDTO personDTO) {
        PersonModel person = modelMapper.map(personDTO, PersonModel.class);
        PersonModel saved = prepareAndSave(personDTO, person);
        return modelMapper.map(saved, PersonResponseDTO.class);
    }

    private PersonModel prepareAndSave(PersonRequestDTO personDTO, PersonModel person) {
        if (personDTO.getAddress() != null) {
            AddressModel address = modelMapper.map(personDTO.getAddress(), AddressModel.class);
            person.setAddress(address);
        }

        if (personDTO.getBankData() != null) {
            BankDataModel bankData = modelMapper.map(personDTO.getBankData(), BankDataModel.class);
            person.setBankData(bankData);
        }

        return personRepository.save(person);
    }

    /**
     * Atualiza os dados de uma pessoa existente.
     */
    @Transactional
    public PersonResponseDTO update(UUID id, PersonRequestDTO personDTO) {
        PersonModel existing = personRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Person not found with id: " + id));

        modelMapper.map(personDTO, existing);
        PersonModel updated = prepareAndSave(personDTO, existing);
        return modelMapper.map(updated, PersonResponseDTO.class);
    }

    /**
     * Busca uma pessoa pelo ID.
     */
    public PersonResponseDTO findById(UUID id) {
        PersonModel person = personRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Person not found with id: " + id));
        return modelMapper.map(person, PersonResponseDTO.class);
    }

    /**
     * Retorna uma lista paginada de pessoas.
     */
    public Page<PersonResponseDTO> findAll(Pageable pageable) {
        return personRepository.findAll(pageable)
                .map(person -> modelMapper.map(person, PersonResponseDTO.class));
    }

    /**
     * Exclui uma pessoa pelo ID.
     */
    @Transactional
    public void deleteById(UUID id) {
        PersonModel person = personRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Person not found with id: " + id));
        personRepository.delete(person);
    }
}

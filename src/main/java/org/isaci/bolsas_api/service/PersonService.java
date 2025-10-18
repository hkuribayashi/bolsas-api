package org.isaci.bolsas_api.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.isaci.bolsas_api.dtos.PersonDTO;
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
    public PersonModel save(PersonDTO personDTO) {
        PersonModel person = modelMapper.map(personDTO, PersonModel.class);

        return getPerson(personDTO, person);
    }

    private PersonModel getPerson(PersonDTO personDTO, PersonModel person) {
        if (personDTO.getAddress() != null) {
            AddressModel address = modelMapper.map(personDTO.getAddress(), AddressModel.class);
            person.setAddress(address);
        }

        if (personDTO.getBankData() != null) {
            BankDataModel bankData = modelMapper.map(personDTO.getBankData(), BankDataModel.class);
            person.setBank(bankData);
        }

        return personRepository.save(person);
    }

    /**
     * Atualiza os dados de uma pessoa existente.
     */
    @Transactional
    public PersonModel update(UUID id, PersonDTO personDTO) {
        PersonModel existingPerson = personRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Person not found with id: " + id));

        modelMapper.map(personDTO, existingPerson);

        return getPerson(personDTO, existingPerson);
    }

    /**
     * Busca uma pessoa pelo ID.
     */
    public PersonModel findById(UUID id) {
        return personRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Person not found with id: " + id));
    }

    /**
     * Retorna uma lista paginada de pessoas.
     */
    public Page<PersonModel> findAll(Pageable pageable) {
        return personRepository.findAll(pageable);
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

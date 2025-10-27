package org.isaci.bolsas_api.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.isaci.bolsas_api.dto.request.PersonRequestDTO;
import org.isaci.bolsas_api.dto.response.PersonResponseDTO;
import org.isaci.bolsas_api.service.PersonService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/admin/persons")
@RequiredArgsConstructor
public class PersonController {

    private final PersonService personService;

    /**
     * Cria uma pessoa.
     */
    @PostMapping
    public ResponseEntity<PersonResponseDTO> createPerson(@RequestBody @Valid PersonRequestDTO personDTO) {
        PersonResponseDTO saved = personService.save(personDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    /**
     * Retorna uma pessoa pelo ‘ID’.
     */
    @GetMapping("/{id}")
    public ResponseEntity<PersonResponseDTO> getPersonById(@PathVariable UUID id) {
        PersonResponseDTO person = personService.findById(id);
        return ResponseEntity.ok(person);
    }

    /**
     * Lista todas as pessoas paginadas.
     */
    @GetMapping
    public ResponseEntity<Page<PersonResponseDTO>> getAllPersons(
            @PageableDefault(sort = "fullName", direction = Sort.Direction.ASC) Pageable pageable) {
        Page<PersonResponseDTO> page = personService.findAll(pageable);
        return ResponseEntity.ok(page);
    }

    /**
     * Atualiza uma pessoa existente.
     */
    @PutMapping("/{id}")
    public ResponseEntity<PersonResponseDTO> updatePerson(@PathVariable UUID id,
                                                          @RequestBody @Valid PersonRequestDTO personDTO) {
        PersonResponseDTO updated = personService.update(id, personDTO);
        return ResponseEntity.ok(updated);
    }

    /**
     * Exclui uma pessoa pelo ‘ID’.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePerson(@PathVariable UUID id) {
        personService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

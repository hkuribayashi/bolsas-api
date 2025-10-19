package org.isaci.bolsas_api.repository;

import org.isaci.bolsas_api.model.ParticipationModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ParticipationRepository extends JpaRepository<ParticipationModel, UUID> {
    List<ParticipationModel> findByPersonId(UUID personId);
}

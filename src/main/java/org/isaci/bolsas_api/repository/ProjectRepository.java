package org.isaci.bolsas_api.repository;

import org.isaci.bolsas_api.model.ProjectModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ProjectRepository  extends JpaRepository<ProjectModel, UUID> {

}



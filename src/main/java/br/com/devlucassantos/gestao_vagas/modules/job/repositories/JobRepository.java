package br.com.devlucassantos.gestao_vagas.modules.job.repositories;

import br.com.devlucassantos.gestao_vagas.modules.job.entities.JobEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface JobRepository extends JpaRepository<JobEntity,UUID> {

    List<JobEntity> findByDescriptionContainingIgnoreCase(String filter);

}

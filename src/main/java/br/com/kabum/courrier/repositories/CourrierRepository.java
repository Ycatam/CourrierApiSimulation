package br.com.kabum.courrier.repositories;

import br.com.kabum.courrier.entities.CourrierEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Interface that allows JPA to handle the DB requisitions.
 */
public interface CourrierRepository extends JpaRepository<CourrierEntity, Long> {
}

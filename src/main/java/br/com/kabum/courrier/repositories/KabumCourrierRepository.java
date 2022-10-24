package br.com.kabum.courrier.repositories;

import br.com.kabum.courrier.entities.KabumCourrier;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Interface that allows JPA to handle the DB requisitions.
 */
public interface KabumCourrierRepository extends JpaRepository<KabumCourrier, Long> {
}

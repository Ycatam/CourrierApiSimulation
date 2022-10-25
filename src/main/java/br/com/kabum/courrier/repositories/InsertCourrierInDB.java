package br.com.kabum.courrier.repositories;

import br.com.kabum.courrier.entities.CourrierEntity;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.math.BigDecimal;

/**
 * Additional class to fill the validation data on DB after the application starts. This approach handle the necessity
 * to fill the data manually through a package or requisition. Also, others courriers can be added too.
 */
@Transactional
@Service
public class InsertCourrierInDB {

    private final CourrierRepository courrierRepository;

    public InsertCourrierInDB(CourrierRepository courrierRepository) {
        this.courrierRepository = courrierRepository;
    }

    @PostConstruct
    private void insertKabumDB(){

        CourrierEntity courrierKabum = CourrierEntity.builder()
                .id(1L)
                .courrierName("Entrega KaBuM")
                .calcConstant(BigDecimal.valueOf(0.2))
                .dueDate(4)
                .maxHeight(140)
                .maxWidth(125)
                .minHeight(5)
                .minWidth(13)
                .build();
        courrierRepository.save(courrierKabum);

        CourrierEntity courrierNinja = CourrierEntity.builder()
                .id(2L)
                .courrierName("Entrega Ninja")
                .calcConstant(BigDecimal.valueOf(0.3))
                .dueDate(6)
                .maxHeight(200)
                .maxWidth(140)
                .minHeight(10)
                .minWidth(6)
                .build();
        courrierRepository.save(courrierNinja);

    }

}

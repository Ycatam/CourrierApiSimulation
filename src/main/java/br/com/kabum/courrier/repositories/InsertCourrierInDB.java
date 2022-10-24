package br.com.kabum.courrier.repositories;

import br.com.kabum.courrier.entities.KabumCourrier;
import br.com.kabum.courrier.entities.NinjaCourrier;
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

    private final KabumCourrierRepository kabumCourrierRepository;

    private final NinjaCourrierRepository ninjaCourrierRepository;

    public InsertCourrierInDB(KabumCourrierRepository kabumCourrierRepository, NinjaCourrierRepository ninjaCourrierRepository) {
        this.kabumCourrierRepository = kabumCourrierRepository;
        this.ninjaCourrierRepository = ninjaCourrierRepository;
    }

    @PostConstruct
    private void insertKabumDB(){

        KabumCourrier kabumCourrier = KabumCourrier.builder()
                .id(1L)
                .calcConstant(BigDecimal.valueOf(0.2))
                .dueDate(4)
                .maxHeight(140)
                .maxWidth(125)
                .minHeight(5)
                .minWidth(13)
                .build();
        kabumCourrierRepository.save(kabumCourrier);

    }

    @PostConstruct
    private void insertNinjaDB(){

        NinjaCourrier ninjaCourrier = NinjaCourrier.builder()
                .id(1L)
                .calcConstant(BigDecimal.valueOf(0.3))
                .dueDate(6)
                .maxHeight(200)
                .maxWidth(140)
                .minHeight(10)
                .minWidth(6)
                .build();
        ninjaCourrierRepository.save(ninjaCourrier);

    }
}

package br.com.kabum.courrier.services;

import br.com.kabum.courrier.dtos.CourrierDto;
import br.com.kabum.courrier.dtos.ResponseDto;
import br.com.kabum.courrier.entities.KabumCourrier;
import br.com.kabum.courrier.entities.NinjaCourrier;
import br.com.kabum.courrier.repositories.KabumCourrierRepository;
import br.com.kabum.courrier.repositories.NinjaCourrierRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

/**
 * Service class that handle the controller requisitions and do the required validations to return a list of the
 * courriers or an empty list if the parameters are not allowed.
 */
@Service
@Transactional
public class CourrierService {

    private final KabumCourrierRepository kabumCourrierRepository;
    private final NinjaCourrierRepository ninjaCourrierRepository;

    //This method can implement spring controlled cache, to reduce de access to DB.
    //To do this, a cache evict method must be created, otherwise the cache will grow indefinitely.
    //For this API, there is two courriers preloaded in DB. So I decided to not implement cache for now.
    public CourrierService(KabumCourrierRepository kabumCourrierRepository, NinjaCourrierRepository ninjaCourrierRepository) {
        this.kabumCourrierRepository = kabumCourrierRepository;
        this.ninjaCourrierRepository = ninjaCourrierRepository;
    }

    public List<ResponseDto> consultCourrier(CourrierDto courrierDto) {

        List<ResponseDto> responseDtoList = new ArrayList<>();

        KabumCourrier kabumCourrier = kabumCourrierRepository.findById(1L).get();
        NinjaCourrier ninjaCourrier = ninjaCourrierRepository.findById(1L).get();

        ResponseDto responseDtoAuxNinja = validateForNinjaCourrier(ninjaCourrier, courrierDto);
        ResponseDto responseDtoAuxKabum = validateForKabumCourrier(kabumCourrier, courrierDto);

        if (responseDtoAuxNinja != null && courrierDto.getWeight().compareTo(BigDecimal.ZERO) > 0){
            responseDtoList.add(responseDtoAuxNinja);
        }

        if (responseDtoAuxKabum != null && courrierDto.getWeight().compareTo(BigDecimal.ZERO) > 0){
        responseDtoList.add(responseDtoAuxKabum);
        }

        return responseDtoList;

    }
    private ResponseDto validateForNinjaCourrier(NinjaCourrier ninjaCourrier, CourrierDto courrierDto) {
        if (courrierDto.getProductDimension().getHeight() > ninjaCourrier.getMaxHeight() || courrierDto.getProductDimension().getHeight() < ninjaCourrier.getMinHeight()){
            return null;
        }

        if (courrierDto.getProductDimension().getWidth() > ninjaCourrier.getMaxWidth() || courrierDto.getProductDimension().getWidth() < ninjaCourrier.getMinWidth()){
            return null;
        }

        return ResponseDto.builder()
                .nome("Entrega Ninja")
                .valor_frete(courrierNinjaShippingCalc(ninjaCourrier.getCalcConstant(), courrierDto.getWeight()))
                .prazo_dias(ninjaCourrier.getDueDate())
                .build();

    }

    private ResponseDto validateForKabumCourrier(KabumCourrier kabumCourrier, CourrierDto courrierDto) {

        if (courrierDto.getProductDimension().getHeight() > kabumCourrier.getMaxHeight() || courrierDto.getProductDimension().getHeight() < kabumCourrier.getMinHeight()){
            return null;
        }

        if (courrierDto.getProductDimension().getWidth() > kabumCourrier.getMaxWidth() || courrierDto.getProductDimension().getWidth() < kabumCourrier.getMinWidth()){
            return null;
        }

        return ResponseDto.builder()
                .nome("Entrega KaBuM")
                .valor_frete(courrierKabumShippingCalc(kabumCourrier.getCalcConstant(), courrierDto.getWeight()))
                .prazo_dias(kabumCourrier.getDueDate())
                .build();

    }

    private BigDecimal courrierNinjaShippingCalc(BigDecimal calcConstant, BigDecimal weight){
        return (calcConstant.multiply(weight).divide(BigDecimal.valueOf(10), 2, RoundingMode.HALF_UP));
    }

    private BigDecimal courrierKabumShippingCalc(BigDecimal calcConstant, BigDecimal weight){
        return (calcConstant.multiply(weight).divide(BigDecimal.valueOf(10), 2, RoundingMode.HALF_UP));
    }
}

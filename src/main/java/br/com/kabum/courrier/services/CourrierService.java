package br.com.kabum.courrier.services;

import br.com.kabum.courrier.dtos.CourrierDto;
import br.com.kabum.courrier.dtos.ResponseDto;
import br.com.kabum.courrier.entities.CourrierEntity;
import br.com.kabum.courrier.repositories.CourrierRepository;
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

    private final CourrierRepository courrierRepository;

    //This method can implement a spring controlled cache, to reduce the access to DB.
    //To do this, a cache evict method must be created, otherwise the cache will grow indefinitely.
    //For this API, there is two courriers preloaded in DB. So I decided to not implement cache for now.
    public CourrierService(CourrierRepository courrierRepository) {
        this.courrierRepository = courrierRepository;
    }

    public List<ResponseDto> consultCourrier(CourrierDto courrierDto) {

        List<ResponseDto> responseDtoList = new ArrayList<>();

        CourrierEntity courrierKabum = courrierRepository.findById(1L).get();
        CourrierEntity courrierNinja = courrierRepository.findById(2L).get();

        if (validateForCourriers(courrierNinja, courrierDto)){
            responseDtoList.add(ninjaCourrierBuilder(courrierNinja, courrierDto));
        }

        if (validateForCourriers(courrierKabum, courrierDto)){
            responseDtoList.add(kabumCourrierBuilder(courrierKabum, courrierDto));
        }

        return responseDtoList;

    }

    private Boolean validateForCourriers(CourrierEntity courrier, CourrierDto courrierDto) {

        if (courrierDto.getProductDimension().getHeight() > courrier.getMaxHeight() || courrierDto.getProductDimension().getHeight() < courrier.getMinHeight()){
            return false;
        }

        if (courrierDto.getProductDimension().getWidth() > courrier.getMaxWidth() || courrierDto.getProductDimension().getWidth() < courrier.getMinWidth()){
            return false;
        }

        if (courrierDto.getWeight().compareTo(BigDecimal.ZERO) <= 0){
            return false;
        }

        return true;
    }

    private ResponseDto ninjaCourrierBuilder(CourrierEntity ninjaCourrier, CourrierDto courrierDto) {
        return ResponseDto.builder()
                .nome("Entrega Ninja")
                .valor_frete(courrierShippingCalc(ninjaCourrier.getCalcConstant(), courrierDto.getWeight()))
                .prazo_dias(ninjaCourrier.getDueDate())
                .build();
    }
    private ResponseDto kabumCourrierBuilder(CourrierEntity kabumCourrier, CourrierDto courrierDto) {
        return ResponseDto.builder()
                .nome("Entrega KaBuM")
                .valor_frete(courrierShippingCalc(kabumCourrier.getCalcConstant(), courrierDto.getWeight()))
                .prazo_dias(kabumCourrier.getDueDate())
                .build();
    }

    private BigDecimal courrierShippingCalc(BigDecimal calcConstant, BigDecimal weight){
        return (calcConstant.multiply(weight).divide(BigDecimal.valueOf(10), 2, RoundingMode.HALF_UP));
    }

}

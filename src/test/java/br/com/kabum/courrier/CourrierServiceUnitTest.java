package br.com.kabum.courrier;

import br.com.kabum.courrier.dtos.CourrierDto;
import br.com.kabum.courrier.dtos.ProductDimensionDto;
import br.com.kabum.courrier.dtos.ResponseDto;
import br.com.kabum.courrier.entities.KabumCourrier;
import br.com.kabum.courrier.entities.NinjaCourrier;
import br.com.kabum.courrier.repositories.KabumCourrierRepository;
import br.com.kabum.courrier.repositories.NinjaCourrierRepository;
import br.com.kabum.courrier.services.CourrierService;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.when;

/**
 * Test for the service class {@link CourrierService}
 */
@RunWith(MockitoJUnitRunner.class)
public class CourrierServiceUnitTest {

    @Mock
    KabumCourrierRepository kabumCourrierRepository;
    @Mock
    NinjaCourrierRepository ninjaCourrierRepository;

    CourrierService courrierService;

    @BeforeEach
    public void beforeEach(){
        this.courrierService = new CourrierService(kabumCourrierRepository, ninjaCourrierRepository);
    }

    ResponseDto responseForKabumDto = ResponseDto.builder()
            .nome("Entrega KaBuM")
            .valor_frete(BigDecimal.valueOf(8))
            .prazo_dias(4)
            .build();

    ResponseDto responseForNinjaDto = ResponseDto.builder()
            .nome("Entrega Ninja")
            .valor_frete(BigDecimal.valueOf(12))
            .prazo_dias(6)
            .build();

    KabumCourrier kabumCourrier = KabumCourrier.builder()
            .id(1L)
            .calcConstant(BigDecimal.valueOf(0.2))
            .dueDate(4)
            .maxHeight(140)
            .maxWidth(125)
            .minHeight(5)
            .minWidth(13)
            .build();

    NinjaCourrier ninjaCourrier = NinjaCourrier.builder()
            .id(1L)
            .calcConstant(BigDecimal.valueOf(0.3))
            .dueDate(6)
            .maxHeight(200)
            .maxWidth(140)
            .minHeight(10)
            .minWidth(6)
            .build();

    @Test
    public void consultCourrier_withValidValuesForBothCourrier_shouldReturnBothCourrier() {
       courrierService = new CourrierService(kabumCourrierRepository, ninjaCourrierRepository);

        CourrierDto courrierDto = CourrierDto.builder()
                .productDimension(ProductDimensionDto.builder()
                        .height(102)
                        .width(40)
                        .build())
                .weight(BigDecimal.valueOf(400))
                .build();

        when(kabumCourrierRepository.findById(1L)).thenReturn(Optional.ofNullable(kabumCourrier));
        when(ninjaCourrierRepository.findById(1L)).thenReturn(Optional.ofNullable(ninjaCourrier));

        List<ResponseDto> listDtoFromService = courrierService.consultCourrier(courrierDto);

        assertEquals("Entrega Ninja", listDtoFromService.get(0).getNome());
        assertEquals(new BigDecimal("12.00"), listDtoFromService.get(0).getValor_frete());
        assertSame(responseForNinjaDto.getPrazo_dias(), listDtoFromService.get(0).getPrazo_dias());
        assertEquals("Entrega KaBuM", listDtoFromService.get(1).getNome());
        assertEquals(new BigDecimal("8.00"), listDtoFromService.get(1).getValor_frete());
        assertSame(responseForKabumDto.getPrazo_dias(), listDtoFromService.get(1).getPrazo_dias());
        assertEquals(2, listDtoFromService.size());
    }

    @Test
    public void consultCourrier_withValidValuesForNinjaCourrier_shouldReturnNinjaCourrier(){
        courrierService = new CourrierService(kabumCourrierRepository, ninjaCourrierRepository);

        CourrierDto courrierDto = CourrierDto.builder()
                .productDimension(ProductDimensionDto.builder()
                        .height(152)
                        .width(50)
                        .build())
                .weight(BigDecimal.valueOf(400))
                .build();

        when(kabumCourrierRepository.findById(1L)).thenReturn(Optional.ofNullable(kabumCourrier));
        when(ninjaCourrierRepository.findById(1L)).thenReturn(Optional.ofNullable(ninjaCourrier));

        List<ResponseDto> listDtoFromService = courrierService.consultCourrier(courrierDto);

        assertEquals("Entrega Ninja", listDtoFromService.get(0).getNome());
        assertEquals(new BigDecimal("12.00"), listDtoFromService.get(0).getValor_frete());
        assertSame(responseForNinjaDto.getPrazo_dias(), listDtoFromService.get(0).getPrazo_dias());
        assertEquals(1, listDtoFromService.size());
    }

    @Test
    public void consultCourrier_withInvalidHeight_shouldReturnEmptyList(){
        courrierService = new CourrierService(kabumCourrierRepository, ninjaCourrierRepository);

        CourrierDto courrierDto = CourrierDto.builder()
                .productDimension(ProductDimensionDto.builder()
                        .height(230)
                        .width(50)
                        .build())
                .weight(BigDecimal.valueOf(400))
                .build();

        when(kabumCourrierRepository.findById(1L)).thenReturn(Optional.ofNullable(kabumCourrier));
        when(ninjaCourrierRepository.findById(1L)).thenReturn(Optional.ofNullable(ninjaCourrier));

        List<ResponseDto> listDtoFromService = courrierService.consultCourrier(courrierDto);

        assertEquals(0, listDtoFromService.size());
    }

    @Test
    public void consultCourrier_withInvalidWidth_shouldReturnEmptyList(){
        courrierService = new CourrierService(kabumCourrierRepository, ninjaCourrierRepository);

        CourrierDto courrierDto = CourrierDto.builder()
                .productDimension(ProductDimensionDto.builder()
                        .height(152)
                        .width(162)
                        .build())
                .weight(BigDecimal.valueOf(400))
                .build();

        when(kabumCourrierRepository.findById(1L)).thenReturn(Optional.ofNullable(kabumCourrier));
        when(ninjaCourrierRepository.findById(1L)).thenReturn(Optional.ofNullable(ninjaCourrier));

        List<ResponseDto> listDtoFromService = courrierService.consultCourrier(courrierDto);

        assertEquals(0, listDtoFromService.size());
    }

    @Test
    public void consultCourrier_withZeroWeightValue_shouldReturnEmptyList(){
        courrierService = new CourrierService(kabumCourrierRepository, ninjaCourrierRepository);

        CourrierDto courrierDto = CourrierDto.builder()
                .productDimension(ProductDimensionDto.builder()
                        .height(152)
                        .width(50)
                        .build())
                .weight(BigDecimal.ZERO)
                .build();

        when(kabumCourrierRepository.findById(1L)).thenReturn(Optional.ofNullable(kabumCourrier));
        when(ninjaCourrierRepository.findById(1L)).thenReturn(Optional.ofNullable(ninjaCourrier));

        List<ResponseDto> listDtoFromService = courrierService.consultCourrier(courrierDto);

        assertEquals(0, listDtoFromService.size());
    }

    @Test
    public void consultCourrier_withNegativeWeightValue_shouldReturnEmptyList(){
        courrierService = new CourrierService(kabumCourrierRepository, ninjaCourrierRepository);

        CourrierDto courrierDto = CourrierDto.builder()
                .productDimension(ProductDimensionDto.builder()
                        .height(152)
                        .width(50)
                        .build())
                .weight(BigDecimal.valueOf(-10))
                .build();

        when(kabumCourrierRepository.findById(1L)).thenReturn(Optional.ofNullable(kabumCourrier));
        when(ninjaCourrierRepository.findById(1L)).thenReturn(Optional.ofNullable(ninjaCourrier));

        List<ResponseDto> listDtoFromService = courrierService.consultCourrier(courrierDto);

        assertEquals(0, listDtoFromService.size());
    }
}

package br.com.kabum.courrier.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * DTO class used to build the JSON response.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResponseDto {

    private String nome;

    private BigDecimal valor_frete;

    private Integer prazo_dias;

}

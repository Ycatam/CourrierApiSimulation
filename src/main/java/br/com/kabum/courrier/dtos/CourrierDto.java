package br.com.kabum.courrier.dtos;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * Main DTO class to decompose the JSON object.
 * The weight attribute could have more validations to return errors for unwanted vales. But due to implementations
 * to return an empty list when the value are incorrect, no validations are made.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CourrierDto {

    @JsonAlias("dimensao")
    @NotNull
    private ProductDimensionDto productDimension;

    @JsonAlias("peso")
    private BigDecimal weight;

}

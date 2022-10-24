package br.com.kabum.courrier.dtos;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

/**
 * Auxiliary DTO class to handle nested JSON object.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDimensionDto {

    @JsonAlias("altura")
    @NotNull
    @Positive
    @NotEmpty
    @NotBlank
    private Integer height;

    @JsonAlias("largura")
    @NotNull
    @Positive
    @NotEmpty
    @NotBlank
    private Integer width;
}

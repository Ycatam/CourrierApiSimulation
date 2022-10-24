package br.com.kabum.courrier.rest;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

/**
 * Util class that has the attributes to build an error message.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Error {

    private HttpStatus httpStatusCode;
    private String message;

}

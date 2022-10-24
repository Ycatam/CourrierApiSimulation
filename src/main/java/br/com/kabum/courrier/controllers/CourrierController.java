package br.com.kabum.courrier.controllers;

import br.com.kabum.courrier.dtos.CourrierDto;
import br.com.kabum.courrier.dtos.ResponseDto;
import br.com.kabum.courrier.services.CourrierService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;


/**
 * Controller class that receives the request via POST method.
 * Receives a DTO as parameter, and return the list with the courriers allowed for the payload or an empty list.
 * Always return status code 200.
 */
@Validated
@RestController
@RequestMapping("/calculofrete")
public class CourrierController {

    private final CourrierService courrierService;

    public CourrierController(CourrierService courrierService){
        this.courrierService = courrierService;
    }

    @PostMapping
    public ResponseEntity<List<ResponseDto>> courrier(@RequestBody @Valid CourrierDto courrierDto){
        return ResponseEntity.ok().body(courrierService.consultCourrier(courrierDto));
    }
}

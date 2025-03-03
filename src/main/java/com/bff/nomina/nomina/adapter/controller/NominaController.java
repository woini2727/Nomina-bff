package com.bff.nomina.nomina.adapter.controller;

import com.bff.nomina.nomina.adapter.controller.model.NominaControllerResponseModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletionStage;

@RestController
@RequestMapping("/api/v1/nomina")
@Slf4j
public class NominaController {

    @GetMapping("/{cuit}")
    public CompletionStage<NominaControllerResponseModel> search(
            @PathVariable final String cuit
    ){
        log.info("Call to get nomina by cuit {}", cuit);

        return null;
    }
}

package com.bff.nomina.nomina.adapter.controller;

import com.bff.nomina.nomina.adapter.controller.model.NominaControllerResponseModel;
import com.bff.nomina.nomina.application.port.in.SearchNominaQuery;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletionStage;

@RestController
@RequestMapping("/api/v1/nomina")
@Slf4j
public class NominaController {
    private final SearchNominaQuery searchNominaQuery;

    public NominaController(final SearchNominaQuery searchNominaQuery) {
        this.searchNominaQuery = searchNominaQuery;
    }


    @GetMapping("/{cuit}")
    public CompletionStage<NominaControllerResponseModel> search(
            @PathVariable final String cuit,
            @RequestParam final String jwt
    ) {
        log.info("Call to get nomina by cuit {}", cuit);
        return searchNominaQuery.getByCuit(cuit, jwt).thenApply(NominaControllerResponseModel::fromDomain);
    }
}

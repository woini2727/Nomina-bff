package com.bff.nomina.nomina.adapter.controller;

import com.bff.nomina.nomina.adapter.controller.model.NominaControllerResponseModel;
import com.bff.nomina.nomina.application.port.in.SearchNominaQuery;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.concurrent.CompletionStage;

@Controller
@RequestMapping("")
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

    /*@GetMapping("/test")
    public String addNewEmployee(Model model) {
        NominaControllerResponseModel nomina = NominaControllerResponseModel.builder()
                        .apellido("AAA").build();
        log.info("xdd");
        model.addAttribute("nomina", Collections.singletonList(nomina) );
        return "index";
    }*/

    @GetMapping("/search")
    public String ss(Model model, @RequestParam final String cuit) {
        NominaControllerResponseModel nomina = NominaControllerResponseModel.builder()
                .apellido("AAA").build();
        log.info(cuit);
        model.addAttribute("nomina", Collections.singletonList(nomina) );
        return "index";
    }

}

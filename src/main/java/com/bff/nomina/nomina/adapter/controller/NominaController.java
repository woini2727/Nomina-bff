package com.bff.nomina.nomina.adapter.controller;

import com.bff.nomina.nomina.adapter.controller.model.NominaControllerResponseModel;
import com.bff.nomina.nomina.application.port.in.SearchNominaQuery;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletionStage;

@Controller
@RequestMapping("")
@Slf4j
public class NominaController {
    private final SearchNominaQuery searchNominaQuery;

    public NominaController(final SearchNominaQuery searchNominaQuery) {
        this.searchNominaQuery = searchNominaQuery;
    }

    @GetMapping("/search")
    public CompletionStage<String> search(Model model, @RequestParam final String cuit, @RequestParam final String jwt) {
        log.info("Call to get nomina by cuit {} and jwt {}", cuit, jwt);
        return searchNominaQuery.getByCuit(cuit, jwt).thenApply(NominaControllerResponseModel::fromDomain)
                .thenApply(nomina -> {
                            model.addAttribute("nomina", nomina);
                            return "index";
                        }
                );

    }

}

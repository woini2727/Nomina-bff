package com.bff.nomina.nomina.application.usecase;

import com.bff.nomina.nomina.application.port.in.SearchNominaQuery;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletionStage;

@Component
@Slf4j
public class SearchNominaUseCase implements SearchNominaQuery {

    @Override
    public CompletionStage<String> getByCuit(String cuit) {
        return null;
    }
}

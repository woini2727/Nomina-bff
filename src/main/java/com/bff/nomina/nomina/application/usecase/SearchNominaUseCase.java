package com.bff.nomina.nomina.application.usecase;

import com.bff.nomina.nomina.application.port.in.SearchNominaQuery;
import com.bff.nomina.nomina.application.port.out.NominaRepository;
import com.bff.nomina.nomina.domain.Nomina;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.Executor;

@Component
@Slf4j
public class SearchNominaUseCase implements SearchNominaQuery {
    private final NominaRepository nominaRepository;
    private final Executor executor;

    public SearchNominaUseCase(NominaRepository nominaRepository,
                               final @Qualifier("asyncExecutor") Executor executor) {
        this.nominaRepository = nominaRepository;
        this.executor = executor;
    }

    @Override
    public CompletionStage<Nomina> getByCuit(String cuit) {
        return CompletableFuture.supplyAsync(() -> doGet(cuit), executor);
    }

    private Nomina doGet(String cuit) {
        return nominaRepository.searchByCuit(cuit);
    }


}

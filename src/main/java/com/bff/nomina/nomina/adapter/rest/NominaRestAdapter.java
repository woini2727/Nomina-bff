package com.bff.nomina.nomina.adapter.rest;

import com.bff.nomina.nomina.adapter.rest.model.NominaRestModel;
import com.bff.nomina.nomina.application.port.out.NominaRepository;
import com.bff.nomina.nomina.config.Config;
import com.bff.nomina.nomina.config.ErrorCode;
import com.bff.nomina.nomina.config.exception.NotFoundException;
import com.bff.nomina.nomina.domain.Nomina;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Repository
@Slf4j
public class NominaRestAdapter implements NominaRepository {
    private final Config config;
    private final RestTemplate restTemplate;

    public NominaRestAdapter(final Config config, final RestTemplate restTemplate) {
        this.config = config;
        this.restTemplate = restTemplate;
    }

    @Override
    public Nomina searchByCuit(String cuit) {
        final String url = config.getNominaRepositoryConfig().getUrl().replace("{cuit}", cuit);
        log.info("Getting Nomina data with CUIT {}", cuit);

        return Optional.of(restTemplate.getForObject(url, NominaRestModel.class))
                .map(model -> {
                    log.info("");
                    return model.toDomain();
                }).orElseThrow(() -> new NotFoundException(ErrorCode.NOMINA_NOT_FOUND));
    }
}

package com.bff.nomina.nomina.adapter.rest;

import com.bff.nomina.nomina.adapter.rest.model.NominaRestModel;
import com.bff.nomina.nomina.application.port.out.NominaRepository;
import com.bff.nomina.nomina.config.Config;
import com.bff.nomina.nomina.config.ErrorCode;
import com.bff.nomina.nomina.config.exception.NotFoundException;
import com.bff.nomina.nomina.domain.Nomina;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Repository
@Slf4j
public class NominaRestAdapter implements NominaRepository {
    private final Config config;
    private final RestTemplate restTemplate;
    private final HeadersProvider headersProvider;

    public NominaRestAdapter(
            final Config config,
            final RestTemplate restTemplate,
            final HeadersProvider headersProvider
    ) {
        this.config = config;
        this.restTemplate = restTemplate;
        this.headersProvider = headersProvider;
    }

    @Override
    public Nomina searchByCuit(String cuit, String jwt) {
        final String url = config.getNominaRepositoryConfig().getUrl() + "/" + cuit;
        log.info("Getting Nomina data with CUIT {} to url {} ", cuit, url);
        final HttpEntity<Void> httpEntity = new HttpEntity<>(headersProvider.get(jwt));

        return Optional.ofNullable(restTemplate.exchange(url, HttpMethod.GET, httpEntity, NominaRestModel.class).getBody())
                .map(model -> {
                    log.info("Got Nomina model {}", model);
                    return model.toDomain();
                }).orElseThrow(() -> new NotFoundException(ErrorCode.NOMINA_NOT_FOUND));
    }
}

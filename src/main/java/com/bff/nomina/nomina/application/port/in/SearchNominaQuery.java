package com.bff.nomina.nomina.application.port.in;

import com.bff.nomina.nomina.domain.Nomina;

import java.util.Optional;
import java.util.concurrent.CompletionStage;

public interface SearchNominaQuery {

    CompletionStage<Optional<Nomina>> getByCuit(String cuit, String jwt);
}

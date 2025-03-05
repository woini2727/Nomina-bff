package com.bff.nomina.nomina.application.port.in;

import com.bff.nomina.nomina.domain.Nomina;

import java.util.concurrent.CompletionStage;

public interface SearchNominaQuery {

    CompletionStage<Nomina> getByCuit(String cuit);
}

package com.bff.nomina.nomina.application.port.in;

import java.util.concurrent.CompletionStage;

public interface SearchNominaQuery {

    CompletionStage<String> getByCuit(String cuit);
}

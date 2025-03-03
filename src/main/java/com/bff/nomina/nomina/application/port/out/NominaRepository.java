package com.bff.nomina.nomina.application.port.out;

import com.bff.nomina.nomina.domain.Nomina;

public interface NominaRepository {

    Nomina searchByCuit(String cuit);
}

package com.bff.nomina.nomina.mock;

import com.bff.nomina.nomina.adapter.rest.model.NominaRestModel;
import com.bff.nomina.nomina.domain.Nomina;

public class NominaMockFactory {

    public static Nomina buildNomina() {
        return Nomina.builder().build();
    }

    public static NominaRestModel buildNominaRestModel() {
        return NominaRestModel.builder().build();
    }
}

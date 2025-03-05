package com.bff.nomina.nomina.adapter.rest.model;

import com.bff.nomina.nomina.domain.Nomina;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Value;

import java.math.BigInteger;

@Value
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class NominaRestModel {
    String apellido;
    String barrio;
    String cargo;
    String celular;
    String dias;
    String domicilio;
    String email;
    String estado;
    String fechaDestitucion;
    String horario;
    String imagen;
    String localidad;
    BigInteger matricula;
    BigInteger registro;
    Boolean mostrarImagen;
    String nombre;
    String sexo;
    String telefono;

    public Nomina toDomain(){
        return Nomina.builder()
                .apellido(apellido)
                .barrio(barrio)
                .cargo(cargo)
                .celular(celular)
                .dias(dias)
                .domicilio(domicilio)
                .email(email)
                .estado(estado)
                .fechaDestitucion(fechaDestitucion)
                .horario(horario)
                .imagen(imagen)
                .localidad(localidad)
                .matricula(matricula)
                .registro(registro)
                .mostrarImagen(mostrarImagen)
                .nombre(nombre)
                .sexo(sexo)
                .telefono(telefono)
                .build();
    }
}

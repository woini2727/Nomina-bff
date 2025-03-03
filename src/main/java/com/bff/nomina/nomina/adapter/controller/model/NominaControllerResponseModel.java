package com.bff.nomina.nomina.adapter.controller.model;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import java.math.BigInteger;

@Builder
@Value
public class NominaControllerResponseModel {
    @NonNull String apellido;
    @NonNull String barrio;
    @NonNull String cargo;
    @NonNull String celular;
    @NonNull String dias;
    @NonNull String domicilio;
    @NonNull String email;
    @NonNull String estado;
    @NonNull String fechaDestitucion;
    @NonNull String horario;
    @NonNull String imagen;
    @NonNull String localidad;
    @NonNull BigInteger matricula;
    @NonNull BigInteger registro;
    @NonNull Boolean mostrarImagen;
    @NonNull String nombre;
    @NonNull String sexo;
    @NonNull String telefono;
}

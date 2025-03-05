package com.bff.nomina.nomina.domain;

import lombok.Builder;
import lombok.Value;

import java.math.BigInteger;

@Builder
@Value
public class Nomina {
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
}

package com.bff.nomina.nomina.adapter.controller.model;

import com.bff.nomina.nomina.domain.Nomina;
import lombok.Builder;
import lombok.Value;

import java.math.BigInteger;

@Builder
@Value
public class NominaControllerResponseModel {
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

    public static NominaControllerResponseModel fromDomain(Nomina domain){
        return NominaControllerResponseModel.builder()
                .apellido(domain.getApellido())
                .barrio(domain.getBarrio())
                .cargo(domain.getCargo())
                .celular(domain.getCelular())
                .dias(domain.getDias())
                .domicilio(domain.getDomicilio())
                .email(domain.getEmail())
                . estado(domain.getEstado())
                .fechaDestitucion(domain.getFechaDestitucion())
                .horario(domain.getHorario())
                .imagen(domain.getImagen())
                .localidad(domain.getLocalidad())
                .matricula(domain.getMatricula())
                .registro(domain.getRegistro())
                .mostrarImagen(domain.getMostrarImagen())
                .nombre(domain.getNombre())
                .sexo(domain.getSexo())
                .telefono(domain.getTelefono()).build();
    }
}

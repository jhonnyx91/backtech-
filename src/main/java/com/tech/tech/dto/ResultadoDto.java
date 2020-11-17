package com.tech.tech.dto;

import lombok.Data;

@Data
public class ResultadoDto {
    private int dia;
    private Integer viajes;

    public ResultadoDto(int dia, Integer viajes) {
        this.dia = dia;
        this.viajes = viajes;
    }
}

package com.tech.tech.service;

import com.tech.tech.dto.RegistroDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IMudanzaService {

    void calcularDias(String dni, MultipartFile file);
    List<RegistroDto> obtenerRegistros();
    Integer calcularViaje(List<Integer> elementos);
}

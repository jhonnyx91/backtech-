package com.tech.tech.controller;


import com.tech.tech.dto.RegistroDto;
import com.tech.tech.service.IMudanzaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/tech")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST})
public class MudanzaController {

    @Autowired
    IMudanzaService mudanzaService;

    @PostMapping("/calcular")
    public void processFile(
            @RequestParam("file") MultipartFile file,
            @RequestParam("dni") String dni) {
        mudanzaService.calcularDias(dni, file);
    }

    @GetMapping("/registros")
    public List<RegistroDto> obtenerResultado() {
        return mudanzaService.
                obtenerRegistros();
    }

}

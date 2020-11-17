package com.tech.tech.service;

import com.tech.tech.dto.RegistroDto;
import com.tech.tech.dto.ResultadoDto;
import com.tech.tech.entity.Registro;
import com.tech.tech.repository.RegistroRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MudanzaServiceImpl implements IMudanzaService {

    public static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss a");

    @Autowired
    private RegistroRepository registroRepository;

    @Override
    public void calcularDias(String dni, MultipartFile file) {
        List<List<Integer>> dias = leerArchivo(file);
        List<ResultadoDto> viajesxDia = new ArrayList<>();

        int numeroDia = 1;
        for (List<Integer> dia : dias) {
            viajesxDia.add(new ResultadoDto(numeroDia++, calcularViaje(dia)));
        }
        Registro registro = new Registro();
        registro.setDni(dni);
        registro.setFecha(LocalDateTime.now().format(formatter));
        registro.setResultado(viajesxDia.toString());
        registroRepository.save(registro);
    }

    @Override
    public Integer calcularViaje(List<Integer> elementos) {
        int viajes = 0;
        List<Integer> bolsa = new ArrayList<>();
        while (!elementos.isEmpty()) {
            Collections.sort(elementos);
            int indexElementoMasPesado = elementos.size() - 1;
            bolsa.add(elementos.get(indexElementoMasPesado));
            elementos.remove(indexElementoMasPesado);
            if (elementos.isEmpty())
                return viajes;
            while (pesoBolsaInferior50(bolsa)) {
                final int indexElementoMenosPesado = 0;
                bolsa.add(elementos.get(indexElementoMenosPesado));
                elementos.remove(indexElementoMenosPesado);
            }
            viajes++;
            bolsa.clear();
        }


        return viajes;
    }

    private boolean pesoBolsaInferior50(List<Integer> bolsa) {
        return bolsa.get(0) * bolsa.size() <= 50;

    }


    public List<List<Integer>> leerArchivo(MultipartFile file) {
        List<Integer> input = new ArrayList<>();
        try {
            InputStreamReader inputStreamReader = new InputStreamReader(file.getInputStream(), Charset.defaultCharset());
            BufferedReader br = new BufferedReader(inputStreamReader);
            String linea;
            while ((linea = br.readLine()) != null) {
                input.add(Integer.valueOf(linea));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<List<Integer>> dias = new ArrayList<>();

        int i = 1;
        while (i < input.size()) {
            int numeroObjetos = input.get(i);
            int rangoInicial = i + 1;
            int rangoFinal = rangoInicial + numeroObjetos;
            List<Integer> objetos = new ArrayList<>(input.subList(rangoInicial, rangoFinal));
            dias.add(objetos);
            i = rangoFinal;
        }

        return dias;

    }


    @Override
    public List<RegistroDto> obtenerRegistros() {
        return registroRepository.findAll(Sort.by("fecha").descending())
                .stream().map(entity -> {
                    RegistroDto registroDto = new RegistroDto();
                    BeanUtils.copyProperties(entity, registroDto);
                    return registroDto;
                }).collect(Collectors.toList());
    }
}

package com.tech.tech.service;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
class MudanzaServiceImplTest {

    private MudanzaServiceImpl mudanzaServiceImpl;


    @Test
    void calcularViaje_debeDevolverelnumerodeviajesporDia() {
        mudanzaServiceImpl = new MudanzaServiceImpl();
        List<Integer> objetos = new ArrayList<>();
        objetos.add(30);
        objetos.add(30);
        objetos.add(1);
        objetos.add(1);

        Integer resultado = mudanzaServiceImpl.calcularViaje(objetos);
        Assert.assertEquals("2", String.valueOf(resultado));
    }


}
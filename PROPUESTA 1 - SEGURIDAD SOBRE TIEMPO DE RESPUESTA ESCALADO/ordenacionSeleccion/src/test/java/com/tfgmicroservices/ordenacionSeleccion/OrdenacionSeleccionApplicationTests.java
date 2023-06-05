package com.tfgmicroservices.ordenacionSeleccion;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.tfgmicroservices.ordenacionSeleccion.controller.OrdenacionSeleccionController;
import com.tfgmicroservices.ordenacionSeleccion.entities.Game;
import com.tfgmicroservices.ordenacionSeleccion.services.OrdenacionSeleccionService;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import org.junit.Assert;
import org.junit.Before;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

@SpringBootTest
public class OrdenacionSeleccionApplicationTests {

	@InjectMocks
	private OrdenacionSeleccionController ordenacionSeleccionController;

	@Mock
	private OrdenacionSeleccionService ordenacionSeleccionService;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testOrdenacionSeleccionVersion() {
        // Datos de prueba
        Collection<Game> inputCollection = Arrays.asList(
                new Game("C", 2000, 4.5),
                new Game("A", 1998, 4.2),
                new Game("B", 2000, 4.2));
        Collection<Game> expectedOutput = Arrays.asList(
                new Game("A", 1998, 4.2),
                new Game("B", 2000, 4.2),
                new Game("C", 2000, 4.5));

        // Mock del servicio
        Mockito.when(ordenacionSeleccionService.ordenacionSeleccion(inputCollection)).thenReturn(expectedOutput);

        // Llamada al controlador
        Collection<Game> result = ordenacionSeleccionController.ordenacionSeleccionVersion(inputCollection);

        // Verificación
        System.out.println(result);
        Mockito.verify(ordenacionSeleccionService).ordenacionSeleccion(inputCollection);
        Assert.assertEquals(expectedOutput, result);
    }

    @Test
    public void testOrdenacionSeleccionVersion_EmptyCollection() {
        // Datos de prueba
        Collection<Game> inputCollection = Collections.emptyList();
        Collection<Game> expectedOutput = Collections.emptyList();

        // Mock del servicio
        Mockito.when(ordenacionSeleccionService.ordenacionSeleccion(inputCollection)).thenReturn(expectedOutput);

        // Llamada al controlador
        Collection<Game> result = ordenacionSeleccionController.ordenacionSeleccionVersion(inputCollection);

        // Verificación
        Mockito.verify(ordenacionSeleccionService).ordenacionSeleccion(inputCollection);
        Assert.assertEquals(expectedOutput, result);
    }

    @Test
    public void testOrdenacionSeleccionVersion_SingleElementCollection() {
        // Datos de prueba
        Game game = new Game("A", 2000, 4.2);
        Collection<Game> inputCollection = Collections.singletonList(game);
        Collection<Game> expectedOutput = Collections.singletonList(game);

        // Mock del servicio
        Mockito.when(ordenacionSeleccionService.ordenacionSeleccion(inputCollection)).thenReturn(expectedOutput);

        // Llamada al controlador
        Collection<Game> result = ordenacionSeleccionController.ordenacionSeleccionVersion(inputCollection);

        // Verificación
        Mockito.verify(ordenacionSeleccionService).ordenacionSeleccion(inputCollection);
        Assert.assertEquals(expectedOutput, result);
    }

	
}

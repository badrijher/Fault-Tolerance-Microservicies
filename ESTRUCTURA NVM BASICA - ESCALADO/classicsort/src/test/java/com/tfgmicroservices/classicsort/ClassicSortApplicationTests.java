package com.tfgmicroservices.classicsort;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import com.tfgmicroservices.classicsort.controller.ClassicSortController;
import com.tfgmicroservices.classicsort.entities.Game;
import com.tfgmicroservices.classicsort.services.ClassicSortService;

@SpringBootTest
public class ClassicSortApplicationTests {

	@InjectMocks
	private ClassicSortController classicSortController;

	@Mock
	private ClassicSortService classicSortService;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

	@Test
    public void testClassicSortVersion() {
        // Datos de prueba
        Collection<Game> inputCollection = Arrays.asList(
                new Game("C", 2000, 4.5),
                new Game("A", 1998, 4.2),
                new Game("B", 2000, 4.2));
        Collection<Game> expectedOutput = Arrays.asList(
                new Game("C", 2000, 4.5),
                new Game("B", 2000, 4.2),
                new Game("A", 1998, 4.2));

        // Mock del servicio
        Mockito.when(classicSortService.classicSortMod(inputCollection)).thenReturn(expectedOutput);

        // Llamada al controlador
        Collection<Game> result = classicSortController.classicSortVersion(inputCollection);

        // Verificación
        Mockito.verify(classicSortService).classicSortMod(inputCollection);
        Assert.assertEquals(expectedOutput, result);
    }

    @Test
    public void testClassicSortVersion_EmptyCollection() {
        // Datos de prueba
        Collection<Game> inputCollection = Collections.emptyList();
        Collection<Game> expectedOutput = Collections.emptyList();

        // Mock del servicio
        Mockito.when(classicSortService.classicSortMod(inputCollection)).thenReturn(expectedOutput);

        // Llamada al controlador
        Collection<Game> result = classicSortController.classicSortVersion(inputCollection);

        // Verificación
        Mockito.verify(classicSortService).classicSortMod(inputCollection);
        Assert.assertEquals(expectedOutput, result);
    }

    @Test
    public void testClassicSortVersion_SingleElementCollection() {
        // Datos de prueba
        Game game = new Game("A", 2000, 4.2);
        Collection<Game> inputCollection = Collections.singletonList(game);
        Collection<Game> expectedOutput = Collections.singletonList(game);

        // Mock del servicio
        Mockito.when(classicSortService.classicSortMod(inputCollection)).thenReturn(expectedOutput);

        // Llamada al controlador
        Collection<Game> result = classicSortController.classicSortVersion(inputCollection);

        // Verificación
        Mockito.verify(classicSortService).classicSortMod(inputCollection);
        Assert.assertEquals(expectedOutput, result);
    }
	

	


}

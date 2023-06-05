package com.tfgmicroservices.bubblesort;

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
import com.tfgmicroservices.bubblesort.controller.BubbleSortController;
import com.tfgmicroservices.bubblesort.entities.Game;
import com.tfgmicroservices.bubblesort.services.BubbleSortService;

@SpringBootTest
public class BubblesortApplicationTests {

	@InjectMocks
	private BubbleSortController bubbleSortController;

	@Mock
	private BubbleSortService bubbleSortService;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

	@Test
    public void testBubbleSortVersion() {
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
        Mockito.when(bubbleSortService.bubbleSort(inputCollection)).thenReturn(expectedOutput);

        // Llamada al controlador
        Collection<Game> result = bubbleSortController.bubbleSortVersion(inputCollection);

        // Verificación
        Mockito.verify(bubbleSortService).bubbleSort(inputCollection);
        Assert.assertEquals(expectedOutput, result);
    }

    @Test
    public void testBubbleSortVersion_EmptyCollection() {
        // Datos de prueba
        Collection<Game> inputCollection = Collections.emptyList();
        Collection<Game> expectedOutput = Collections.emptyList();

        // Mock del servicio
        Mockito.when(bubbleSortService.bubbleSort(inputCollection)).thenReturn(expectedOutput);

        // Llamada al controlador
        Collection<Game> result = bubbleSortController.bubbleSortVersion(inputCollection);

        // Verificación
        Mockito.verify(bubbleSortService).bubbleSort(inputCollection);
        Assert.assertEquals(expectedOutput, result);
    }

    @Test
    public void testBubbleSortVersion_SingleElementCollection() {
        // Datos de prueba
        Game game = new Game("A", 2000, 4.2);
        Collection<Game> inputCollection = Collections.singletonList(game);
        Collection<Game> expectedOutput = Collections.singletonList(game);

        // Mock del servicio
        Mockito.when(bubbleSortService.bubbleSort(inputCollection)).thenReturn(expectedOutput);

        // Llamada al controlador
        Collection<Game> result = bubbleSortController.bubbleSortVersion(inputCollection);

        // Verificación
        Mockito.verify(bubbleSortService).bubbleSort(inputCollection);
        Assert.assertEquals(expectedOutput, result);
    }
	


}

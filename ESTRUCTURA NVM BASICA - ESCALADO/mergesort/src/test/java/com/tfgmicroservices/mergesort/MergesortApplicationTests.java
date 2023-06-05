package com.tfgmicroservices.mergesort;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.tfgmicroservices.mergesort.controller.MergeSortController;
import com.tfgmicroservices.mergesort.entities.Game;
import com.tfgmicroservices.mergesort.services.MergeSortService;

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
public class MergesortApplicationTests {

	@InjectMocks
	private MergeSortController mergeSortController;

	@Mock
	private MergeSortService mergeSortService;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

	@Test
    public void testMergeSortVersion() {
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
        Mockito.when(mergeSortService.ordenarMergeSort(inputCollection)).thenReturn(expectedOutput);

        // Llamada al controlador
        Collection<Game> result = mergeSortController.mergeSortVersion(inputCollection);

        // Verificación
        Mockito.verify(mergeSortService).ordenarMergeSort(inputCollection);
        Assert.assertEquals(expectedOutput, result);
    }

    @Test
    public void testMergeSortVersion_EmptyCollection() {
        // Datos de prueba
        Collection<Game> inputCollection = Collections.emptyList();
        Collection<Game> expectedOutput = Collections.emptyList();

        // Mock del servicio
        Mockito.when(mergeSortService.ordenarMergeSort(inputCollection)).thenReturn(expectedOutput);

        // Llamada al controlador
        Collection<Game> result = mergeSortController.mergeSortVersion(inputCollection);

        // Verificación
        Mockito.verify(mergeSortService).ordenarMergeSort(inputCollection);
        Assert.assertEquals(expectedOutput, result);
    }

    @Test
    public void testMergeSortVersion_SingleElementCollection() {
        // Datos de prueba
        Game game = new Game("A", 2000, 4.2);
        Collection<Game> inputCollection = Collections.singletonList(game);
        Collection<Game> expectedOutput = Collections.singletonList(game);

        // Mock del servicio
        Mockito.when(mergeSortService.ordenarMergeSort(inputCollection)).thenReturn(expectedOutput);

        // Llamada al controlador
        Collection<Game> result = mergeSortController.mergeSortVersion(inputCollection);

        // Verificación
        Mockito.verify(mergeSortService).ordenarMergeSort(inputCollection);
        Assert.assertEquals(expectedOutput, result);
    }
}

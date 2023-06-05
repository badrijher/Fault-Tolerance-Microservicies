package com.tfgmicroservices.fallbackservice;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import com.tfgmicroservices.fallbackservice.controller.FallbackController;
import com.tfgmicroservices.fallbackservice.entities.Game;
import com.tfgmicroservices.fallbackservice.services.FallbackService;


@SpringBootTest
public class FallbackserviceApplicationTests {

	@InjectMocks
    private FallbackController fallbackController;

    @Mock
    private FallbackService fallbackService;

    @Test
    public void testBackupAlgorithm() {
        // Datos de prueba
        String microserviceName = "microservice1";
        Collection<Game> inputCollection = Arrays.asList(
            new Game("C", 2000, 4.5),
            new Game("A", 1998, 4.2),
            new Game("B", 2000, 4.2)
        );
        Collection<Game> expectedOutput = Arrays.asList(
            new Game("A", 1998, 4.2),
            new Game("B", 2000, 4.2),
            new Game("C", 2000, 4.5)
        );

        // Mock del servicio
        Mockito.when(fallbackService.backupAlgorithm(microserviceName, inputCollection)).thenReturn(expectedOutput);

        // Llamada al controlador
        Collection<Game> result = fallbackController.backupAlgorithm(microserviceName, inputCollection);

        // Verificación
        Mockito.verify(fallbackService).backupAlgorithm(microserviceName, inputCollection);
        Assert.assertEquals(expectedOutput, result);
    }

    @Test
    public void testBackupAlgorithm_MicroserviceNameError() {
        // Datos de prueba
        String microserviceName = "microservice1";
        Collection<Game> inputCollection = Arrays.asList(
                new Game("C", 2000, 4.5),
                new Game("A", 1998, 4.2),
                new Game("B", 2000, 4.2));
        Collection<Game> expectedOutput = Collections.emptyList();

        // Mock del servicio
        Mockito.when(fallbackService.backupAlgorithm(microserviceName, inputCollection)).thenReturn(expectedOutput);

        // Llamada al controlador
        Collection<Game> result = fallbackController.backupAlgorithm("invalidMicroservice", inputCollection);

        // Verificación
        Mockito.verify(fallbackService).backupAlgorithm("invalidMicroservice", inputCollection);
        Assert.assertEquals(expectedOutput, result);
    }

    @Test
    public void testBackupAlgorithm_EmptyCollection() {
        // Datos de prueba
        String microserviceName = "microservice1";
        Collection<Game> inputCollection = Collections.emptyList();
        Collection<Game> expectedOutput = Collections.emptyList();

        // Mock del servicio
        Mockito.when(fallbackService.backupAlgorithm(microserviceName, inputCollection)).thenReturn(expectedOutput);

        // Llamada al controlador
        Collection<Game> result = fallbackController.backupAlgorithm(microserviceName, inputCollection);

        // Verificación
        Mockito.verify(fallbackService).backupAlgorithm(microserviceName, inputCollection);
        Assert.assertEquals(expectedOutput, result);
    }

    @Test
    public void testBackupAlgorithm_SingleElementCollection() {
        // Datos de prueba
        String microserviceName = "microservice1";
        Game game = new Game("A", 2000, 4.2);
        Collection<Game> inputCollection = Collections.singletonList(game);
        Collection<Game> expectedOutput = Collections.singletonList(game);

        // Mock del servicio
        Mockito.when(fallbackService.backupAlgorithm(microserviceName, inputCollection)).thenReturn(expectedOutput);

        // Llamada al controlador
        Collection<Game> result = fallbackController.backupAlgorithm(microserviceName, inputCollection);

        // Verificación
        Mockito.verify(fallbackService).backupAlgorithm(microserviceName, inputCollection);
        Assert.assertEquals(expectedOutput, result);
    }



}

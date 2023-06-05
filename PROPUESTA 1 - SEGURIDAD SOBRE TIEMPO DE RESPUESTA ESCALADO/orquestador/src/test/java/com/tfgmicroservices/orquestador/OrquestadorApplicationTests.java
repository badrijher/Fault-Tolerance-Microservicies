package com.tfgmicroservices.orquestador;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.tfgmicroservices.orquestador.entities.Game;
import com.tfgmicroservices.orquestador.services.MicroserviceClient;

import org.springframework.http.*;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class OrquestadorApplicationTests {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private MicroserviceClient microserviceClient;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        microserviceClient = new MicroserviceClient(restTemplate);
        
    }

    @Test
    public void testCallBackupAlgorithm_SuccessfulCall() {
        // Mocking the response from the backup algorithm endpoint
        Collection<Game> mockResponse = new ArrayList<>();
        when(restTemplate.postForObject(anyString(), any(HttpEntity.class), eq(Collection.class)))
                .thenReturn(mockResponse);

        String algorithmName = "BubbleSort";
        Collection<Game> collection = new ArrayList<>();
        Collection<Game> result = microserviceClient.callBackupAlgorithm(algorithmName, collection);

        assertEquals(mockResponse, result);
        verify(restTemplate, times(1)).postForObject(anyString(), any(HttpEntity.class), eq(Collection.class));
    }

    @Test
    public void testCallBackupAlgorithm_EmptyCollection() {
        String algorithmName = "BubbleSort";
        Collection<Game> collection = new ArrayList<>();

        // Mocking the response from the backup algorithm endpoint with an empty
        // collection
        when(restTemplate.postForObject(anyString(), any(HttpEntity.class), eq(Collection.class)))
                        .thenReturn(Collections.emptyList());

        Collection<Game> result = microserviceClient.callBackupAlgorithm(algorithmName, collection);

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(restTemplate, times(1)).postForObject(anyString(), any(HttpEntity.class), eq(Collection.class));
}

    @Test
    public void testCallBackupAlgorithm_ExceptionThrown() {
        // Mocking an exception when calling the backup algorithm endpoint
        when(restTemplate.postForObject(anyString(), any(HttpEntity.class), eq(Collection.class)))
                .thenThrow(new RuntimeException("Error"));

        String algorithmName = "BubbleSort";
        Collection<Game> collection = new ArrayList<>();
        Collection<Game> result = microserviceClient.callBackupAlgorithm(algorithmName, collection);

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(restTemplate, times(1)).postForObject(anyString(), any(HttpEntity.class), eq(Collection.class));
    }

    @Test
    public void testCallAndAnalyzeRawgApi_SuccessfulCall() {
        // Mocking the response from the Rawg API
        String mockResponse = "{ \"results\": [ { \"name\": \"Destiny 2\", \"released\": \"2017-09-06\", \"rating\": 3.55 }, { \"name\": \"Counter-Strike: Global Offensive\", \"released\": \"2012-08-21\", \"rating\": 3.57 } ] }";
        ResponseEntity<String> response = new ResponseEntity<>(mockResponse, HttpStatus.OK);

        when(restTemplate.exchange(anyString(), eq(HttpMethod.GET), any(HttpEntity.class), eq(String.class)))
                .thenReturn(response);

        // Expected game collection
        Collection<Game> expectedGames = new ArrayList<>();
        expectedGames.add(new Game("Destiny 2", 2017, 3.55));
        expectedGames.add(new Game("Counter-Strike: Global Offensive", 2012, 3.57));

        // Call the method
        Collection<Game> result = microserviceClient.callAndAnalyzeRawgApi();

        // Verify the mock interaction
        verify(restTemplate, times(1)).exchange(anyString(), eq(HttpMethod.GET), any(HttpEntity.class), eq(String.class));

        // Assert the result
        assertEquals(expectedGames, result);
    }

    

    @Test
    public void testCallAndAnalyzeRawgApi_EmptyResponse() {
        ResponseEntity<String> response = new ResponseEntity<>(HttpStatus.OK);

        when(restTemplate.exchange(anyString(), eq(HttpMethod.GET), any(HttpEntity.class), eq(String.class)))
                .thenReturn(response);

        // Call the method
        Collection<Game> result = microserviceClient.callAndAnalyzeRawgApi();

        // Assert the result
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    public void testCallAndAnalyzeRawgApi_InvalidResponse() {
            String mockResponse = "{ \"results\": [] }"; // Respuesta vacía, pero en formato JSON válido
            ResponseEntity<String> response = new ResponseEntity<>(mockResponse, HttpStatus.OK);

            when(restTemplate.exchange(anyString(), eq(HttpMethod.GET), any(HttpEntity.class), eq(String.class)))
                            .thenReturn(response);

            // Call the method
            Collection<Game> result = microserviceClient.callAndAnalyzeRawgApi();

            // Assert the result
            assertNotNull(result);
            assertTrue(result.isEmpty());
    }

    @Test
    public void testCallAndAnalyzeRawgApi_Sorting() {
        String mockResponse = "{ \"results\": [ { \"name\": \"Counter-Strike: Global Offensive\", \"released\": \"2012-08-21\", \"rating\": 3.57 }, { \"name\": \"Destiny 2\", \"released\": \"2017-09-06\", \"rating\": 3.55 } ] }";
        ResponseEntity<String> response = new ResponseEntity<>(mockResponse, HttpStatus.OK);

        when(restTemplate.exchange(anyString(), eq(HttpMethod.GET), any(HttpEntity.class), eq(String.class)))
                .thenReturn(response);

        // Expected game collection
        Collection<Game> expectedGames = new ArrayList<>();
        expectedGames.add(new Game("Destiny 2", 2017, 3.55));
        expectedGames.add(new Game("Counter-Strike: Global Offensive", 2012, 3.57));
        

        // Call the method
        Collection<Game> result = microserviceClient.callAndAnalyzeRawgApi();

        // Assert the result
        assertEquals(expectedGames, result);
    }

    @Test
    public void testCallVersions_AllMicroservicesWorking() {
        // Mock de las respuestas exitosas de los microservicios
        Collection<Game> collection = new ArrayList<>();
        Collection<Game> expectedResponse = new ArrayList<>();
        ResponseEntity<Collection<Game>> responseEntity = new ResponseEntity<>(expectedResponse, HttpStatus.OK);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        Mockito.when(restTemplate.exchange(Mockito.anyString(), Mockito.eq(HttpMethod.GET),
                Mockito.any(HttpEntity.class), Mockito.eq(String.class)))
                .thenReturn((ResponseEntity<String>) (ResponseEntity<?>) responseEntity);
        Mockito.when(restTemplate.postForObject(Mockito.anyString(), Mockito.any(), Mockito.eq(Collection.class)))
                .thenReturn(expectedResponse);

        // Llamada al método y verificación del resultado
        Map<String, Object> response = microserviceClient.callVersions(collection);

        // Verificar que se llamó a todos los microservicios sin recurrir al fallback
        assertEquals(expectedResponse, response.get("BUBBLE SORT"));
        assertEquals(expectedResponse, response.get("ORDENACION SELECCION"));
        assertEquals(expectedResponse, response.get("CLASSIC SORT"));
        assertEquals(expectedResponse, response.get("MERGE SORT"));
    }

    @Test
    public void testCallVersions_AllMicroservicesNotWorking() {
        // Mock de las respuestas fallidas de los microservicios
        Collection<Game> collection = new ArrayList<>();
        Collection<Game> expectedFallbackResponse = new ArrayList<>();
        ResponseEntity<Collection<Game>> errorResponseEntity = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

        // Mock del RestTemplate para devolver respuestas fallidas al llamar a los
        // microservicios
        Mockito.when(restTemplate.exchange(Mockito.anyString(), Mockito.eq(HttpMethod.GET),
                Mockito.any(HttpEntity.class), Mockito.eq(String.class))).thenReturn((ResponseEntity<String>) (ResponseEntity<?>) errorResponseEntity);
        Mockito.when(restTemplate.postForObject(Mockito.anyString(), Mockito.any(), Mockito.eq(Collection.class)))
                .thenThrow(new RestClientException("Microservice unavailable"));

        // Llamada al método y verificación del resultado
        Map<String, Object> response = microserviceClient.callVersions(collection);

        // Verificar que se llamó al método de fallback y se devolvió la respuesta
        // esperada
        assertEquals(expectedFallbackResponse, response.get("BUBBLE SORT"));
        assertEquals(expectedFallbackResponse, response.get("ORDENACION SELECCION"));
        assertEquals(expectedFallbackResponse, response.get("CLASSIC SORT"));
        assertEquals(expectedFallbackResponse, response.get("MERGE SORT"));
        assertTrue((boolean) response.get("fallbackAlgorithm"));
        assertEquals(Arrays.asList("BubbleSort", "OrdenacionSeleccion", "ClassicSort", "MergeSort"),
                response.get("failedMicroservices"));
    }

    @Test
    public void testCallVersions_SomeMicroservicesNotWorking() {
        // Mock de las respuestas exitosas de los microservicios funcionales
        Collection<Game> expectedResponse = new ArrayList<>();
        ResponseEntity<Collection<Game>> responseEntity = new ResponseEntity<>(expectedResponse, HttpStatus.OK);
        Mockito.lenient().when(restTemplate.exchange(Mockito.anyString(), Mockito.eq(HttpMethod.GET),
                Mockito.any(HttpEntity.class), Mockito.eq(String.class))).thenReturn((ResponseEntity<String>) (ResponseEntity<?>) responseEntity);
        Mockito.lenient()
                .when(restTemplate.postForObject(Mockito.anyString(), Mockito.any(), Mockito.eq(Collection.class)))
                .thenReturn(expectedResponse);

        // Mock de las respuestas fallidas de los microservicios no funcionales
        Collection<Game> expectedFallbackResponse = new ArrayList<>();
        ResponseEntity<Collection<Game>> errorResponseEntity = new ResponseEntity<>(expectedFallbackResponse,
                HttpStatus.INTERNAL_SERVER_ERROR);
        Mockito.lenient().when(restTemplate.exchange(Mockito.eq("fallbackEndpoint"), Mockito.eq(HttpMethod.GET),
                Mockito.any(HttpEntity.class), Mockito.eq(String.class))).thenReturn((ResponseEntity<String>) (ResponseEntity<?>) errorResponseEntity);
        Mockito.lenient().when(
                restTemplate.postForObject(Mockito.eq("fallbackEndpoint"), Mockito.any(), Mockito.eq(Collection.class)))
                .thenReturn(expectedFallbackResponse);

        // Llamada al método y verificación del resultado
        Map<String, Object> response = microserviceClient.callVersions(new ArrayList<>());

        // Verificar que se llamó al método de fallback y que se devolvió la respuesta
        // esperada
        assertEquals(expectedFallbackResponse, response.get("BUBBLE SORT"));
        assertEquals(expectedResponse, response.get("ORDENACION SELECCION"));
        assertEquals(expectedFallbackResponse, response.get("CLASSIC SORT"));
        assertEquals(expectedResponse, response.get("MERGE SORT"));
        assertEquals(expectedResponse, response.get("RAWG API"));
    }

    @Test
    public void testCallVersions_AllMicroservicesAndFallBackFail() {
        // Mock de las respuestas exitosas de los microservicios
        Collection<Game> collection = new ArrayList<>();
        Collection<Game> expectedResponse = new ArrayList<>();
        ResponseEntity<Collection<Game>> responseEntity = new ResponseEntity<>(expectedResponse, HttpStatus.OK);
        Mockito.when(restTemplate.exchange(Mockito.anyString(), Mockito.eq(HttpMethod.GET),
                Mockito.any(HttpEntity.class), Mockito.eq(String.class)))
                .thenReturn((ResponseEntity<String>) (ResponseEntity<?>) responseEntity);
        Mockito.when(restTemplate.postForObject(Mockito.anyString(), Mockito.any(), Mockito.eq(Collection.class)))
                .thenReturn(expectedResponse);

        // Mock de respuesta nula para el método de fallback
        Mockito.lenient().when(
                restTemplate.exchange(Mockito.eq("fallbackEndpoint"), Mockito.eq(HttpMethod.GET),
                        Mockito.any(HttpEntity.class), Mockito.eq(String.class)))
                .thenReturn(null);
        Mockito.lenient().when(
                restTemplate.postForObject(Mockito.eq("fallbackEndpoint"), Mockito.any(), Mockito.eq(Collection.class)))
                .thenReturn(null);

        // Llamada al método y verificación del resultado
        Map<String, Object> response = microserviceClient.callVersions(collection);

        // Verificar que se llamó a todos los microservicios sin recurrir al fallback y
        // se devuelve una respuesta vacía
        assertEquals(expectedResponse, response.get("BUBBLE SORT"));
        assertEquals(expectedResponse, response.get("ORDENACION SELECCION"));
        assertEquals(expectedResponse, response.get("CLASSIC SORT"));
        assertEquals(expectedResponse, response.get("MERGE SORT"));
    }
    
}




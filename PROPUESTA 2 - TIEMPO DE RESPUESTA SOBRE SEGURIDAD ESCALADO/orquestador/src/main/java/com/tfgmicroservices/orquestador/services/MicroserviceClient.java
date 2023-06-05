package com.tfgmicroservices.orquestador.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tfgmicroservices.orquestador.entities.Game;

@Service
public class MicroserviceClient {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${bubblesort.url}")
    private String bubblesortUrl;

    @Value("${ordenacionseleccion.url}")
    private String ordenacionseleccionUrl;

    @Value("${classicsort.url}")
    private String classicsortUrl;

    @Value("${mergesort.url}")
    private String mergesortUrl;

    @Value("${backupAlgorithm.url}")
    private String backupAlgorithmUrl;

    @Value("${rawgapi.url}")
    private String rawgApiUrl;

    public MicroserviceClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
    
    //MÉTODO PARA LLAMAR AL MÉTODO DE FALLBACK
    public Collection<Game> callBackupAlgorithm(String algorithmName, Collection<Game> collection) {
        String url = backupAlgorithmUrl + "/backup/" + algorithmName;
    
        try {
            HttpEntity<Collection<Game>> requestEntity = new HttpEntity<>(collection);
            return restTemplate.postForObject(url, requestEntity, Collection.class);
        } catch (Exception e) {
            System.out.println("Error al llamar al microservicio de fallback para " + algorithmName);
            return new ArrayList<>();
        }
    }

    // MÉTODO PARA LLAMAR A LA API DE RAWG Y ANALIZAR LA RESPUESTA
    public Collection<Game> callAndAnalyzeRawgApi() {
        String url = "https://api.rawg.io/api/games?key=a5eb755e15fe47f1a1aaa9b1fc82cf55";
        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "My-App");

        HttpEntity<String> requestEntity = new HttpEntity<>(headers);

        try {
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, requestEntity, String.class);

            if (response != null && response.getBody() != null) {
                String responseBody = response.getBody();
                ObjectMapper objectMapper = new ObjectMapper();

                Collection<Game> gameInfoList = new ArrayList<>();

                try {
                    JsonNode root = objectMapper.readTree(responseBody);
                    JsonNode resultsNode = root.get("results");

                    if (resultsNode.isArray()) {
                        for (JsonNode gameNode : resultsNode) {
                            String name = gameNode.get("name").asText();
                            String released = gameNode.get("released").asText();
                            double rating = gameNode.get("rating").asDouble();

                            // Obtener solo el año de la fecha de estreno
                            String[] parts = released.split("-");
                            int year = Integer.parseInt(parts[0]);

                            Game gameInfo = new Game(name, year, rating);
                            gameInfoList.add(gameInfo);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                // Ordenar la lista utilizando Quicksort
                quicksort(gameInfoList, 0, gameInfoList.size() - 1);
                return gameInfoList;
            } else {
                // Manejar el caso cuando la respuesta es nula
                return Collections.emptyList();
            }
        } catch (RestClientException e) {
            e.printStackTrace();
            // Manejar el caso cuando se produce un error en la llamada a la API
            return Collections.emptyList();
        }
    }

    // Implementación del algoritmo de Quicksort
    private void quicksort(Collection<Game> gameList, int low, int high) {
        if (low < high) {
            int pivotIndex = partition(gameList, low, high);
            quicksort(gameList, low, pivotIndex - 1);
            quicksort(gameList, pivotIndex + 1, high);
        }
    }

    private int partition(Collection<Game> gameList, int low, int high) {
        Game pivot = null;
        int i = 0;
        for (Game game : gameList) {
            if (i == high) {
                pivot = game;
                break;
            }
            i++;
        }
        i = low - 1;
        for (int j = low; j < high; j++) {
            Game currentGame = null;
            int k = 0;
            for (Game game : gameList) {
                if (k == j) {
                    currentGame = game;
                    break;
                }
                k++;
            }
            if (currentGame != null && currentGame.compareTo(pivot) <= 0) {
                i++;
                swap(gameList, i, j);
            }
        }
        swap(gameList, i + 1, high);
        return i + 1;
    }

    private void swap(Collection<Game> gameList, int i, int j) {
        Game[] gameArray = gameList.toArray(new Game[0]);
        Game temp = gameArray[i];
        gameArray[i] = gameArray[j];
        gameArray[j] = temp;
        gameList.clear();
        gameList.addAll(Arrays.asList(gameArray));
    }
    
    

    // MÉTODO PARA LLAMAR A LAS DISTINTAS VERSIONES
    public Map<String, Object> callVersions(Collection<Game> collection) {

        List<Map<String, Object>> microservicesWithTimes = new ArrayList<>();
        List<String> failedMicroservices = new ArrayList<>();


        Collection<Game> bubble = null;
        Map<String, Object> bubbleResult = new LinkedHashMap<>();
        long startTime = System.currentTimeMillis();
        long bubbleTime = System.currentTimeMillis() - startTime;
        try {
            bubble = restTemplate.postForObject(bubblesortUrl + "/bubblesort", collection, Collection.class);
            bubbleResult.put("service", "BubbleSort");
            bubbleResult.put("time", bubbleTime);
            bubbleResult.put("result", bubble);
            microservicesWithTimes.add(bubbleResult);
        } catch (Exception e) {
            // Manejo del error y llamada al fallbackAlgorithm
            failedMicroservices.add("BubbleSort");
            bubble = callBackupAlgorithm("BubbleSort", new ArrayList<>(collection));
            bubbleResult.put("service", "BubbleSort BACKUP");
            bubbleResult.put("time", bubbleTime);
            bubbleResult.put("result", bubble);
            microservicesWithTimes.add(bubbleResult);

}

        Collection<Game> ordenacionSelec = null;
        Map<String, Object> ordenacionSelecResult = new LinkedHashMap<>();
        long ordenacionSelecTime = System.currentTimeMillis() - startTime;
        try {
            ordenacionSelec = restTemplate.postForObject(ordenacionseleccionUrl + "/ordenacionseleccion", collection,
            Collection.class);            
            ordenacionSelecResult.put("service", "OrdenacionSeleccion");
            ordenacionSelecResult.put("time", ordenacionSelecTime);
            ordenacionSelecResult.put("result", ordenacionSelec);
            microservicesWithTimes.add(ordenacionSelecResult);
        } catch (Exception e) {
            // Manejo del error y llamada al fallbackAlgorithm
            failedMicroservices.add("Ordenacion Seleccion");
            ordenacionSelec = callBackupAlgorithm("OrdenacionSeleccion", new ArrayList<>(collection));
            ordenacionSelecResult.put("service", "OrdenacionSeleccion BACKUP");
            ordenacionSelecResult.put("time", ordenacionSelecTime);
            ordenacionSelecResult.put("result", ordenacionSelec);
            microservicesWithTimes.add(ordenacionSelecResult);
}

        Collection<Game> classic = null;
        Map<String, Object> classicResult = new LinkedHashMap<>();
        long classicTime = System.currentTimeMillis() - startTime;
        try {
            classic = restTemplate.postForObject(classicsortUrl + "/classicsort", collection,
                    Collection.class);            
            classicResult.put("service", "ClassicSort");
            classicResult.put("time", classicTime);
            classicResult.put("result", classic);
            microservicesWithTimes.add(classicResult);
        } catch (Exception e) {
            // Manejo del error y llamada al fallbackAlgorithm
            failedMicroservices.add("ClassicSort");
            classic = callBackupAlgorithm("ClassicSort", new ArrayList<>(collection));
            classicResult.put("service", "ClassicSort BACKUP");
            classicResult.put("time", classicTime);
            classicResult.put("result", classic);
            microservicesWithTimes.add(classicResult);
        }


        Collection<Game> merge = null;
        Map<String, Object> mergeResult = new LinkedHashMap<>();
        long mergeTime = System.currentTimeMillis() - startTime;
        try {
            merge = restTemplate.postForObject(mergesortUrl + "/mergesort", collection,
                    Collection.class);            
            mergeResult.put("service", "MergeSort");
            mergeResult.put("time", mergeTime);
            mergeResult.put("result", merge);
            microservicesWithTimes.add(mergeResult);
        } catch (Exception e) {
            // Manejo del error y llamada al fallbackAlgorithm
            failedMicroservices.add("MergeSort");
            merge = callBackupAlgorithm("MergeSort", new ArrayList<>(collection));
            mergeResult.put("service", "MergeSort BACKUP");
            mergeResult.put("time", mergeTime);
            mergeResult.put("result", merge);
            microservicesWithTimes.add(mergeResult);

        }
        
        Collection<Game> rawgApi = null;
        Map<String, Object> rawgApiResult = new LinkedHashMap<>();
        long rawgTime = System.currentTimeMillis() - startTime;
        try {
            rawgApi = callAndAnalyzeRawgApi();            
            rawgApiResult.put("service", "RAWG API");
            rawgApiResult.put("time", rawgTime);
            rawgApiResult.put("result", rawgApi);
            microservicesWithTimes.add(rawgApiResult);
        } catch (Exception e) {
            // Manejo del error y llamada al fallbackAlgorithm
            failedMicroservices.add("RAWG API");
            rawgApi = callBackupAlgorithm("RAWG", new ArrayList<>(collection));
            rawgApiResult.put("service", "RAWG API BACKUP");
            rawgApiResult.put("time", rawgTime);
            rawgApiResult.put("result", rawgApi);
            microservicesWithTimes.add(rawgApiResult);
        }
        
        Map<String, Object> response = new LinkedHashMap<>();
        Map<String, Object> selectedMicroservice;
        
        if (!microservicesWithTimes.isEmpty()) {
            // Seleccionar el microservicio con menor tiempo de respuesta y resultado no vacío
            selectedMicroservice = microservicesWithTimes.stream()
                    .filter(microservice -> {
                        Object result = microservice.get("result");
                        return result != null && result instanceof Collection && !((Collection<?>) result).isEmpty();
                    })
                    .min(Comparator.comparingLong(microservice -> (long) microservice.get("time")))
                    .orElse(null);
                
        // Agrega el microservicio seleccionado y su tiempo de respuesta al 'response'
        response.put("VOTING", selectedMicroservice);
        System.out.println(selectedMicroservice.get("result"));
    }
       
        // Agregar los campos de fallbackAlgorithm y failedMicroservices
        response.put("fallbackAlgorithm", !failedMicroservices.isEmpty());
        response.put("failedMicroservices", failedMicroservices);

        //Versiones con sus respectivos tiempos de respuesta
        response.put("MICROSERVICES WITH TIME", microservicesWithTimes);
        return response;
    }

    //VOTADOR POR CONCENSO
    public Collection<Game> consensusVoting(Collection<Game> collection) {

        // Resultados de cada algoritmo
        Collection<Game> bubble = null;
        Collection<Game> ordenacionSelec = null;
        Collection<Game> classic = null;
        Collection<Game> merge = null;
        Collection<Game> rawgapi = null;
    
        // Contadores de cada resultado
        int bubbleCount = 0;
        int ordenacionSelecCount = 0;
        int classicCount = 0;
        int mergeCount = 0;
        int rawgapiCount = 0;
    
        // Realizar la votación por consenso
        if (collection != null && !collection.isEmpty()) {
            try {
                bubble = restTemplate.postForObject(bubblesortUrl + "/bubblesort", collection, Collection.class);
                bubbleCount++;
            } catch (Exception e) {}
    
            try {
                ordenacionSelec = restTemplate.postForObject(ordenacionseleccionUrl + "/ordenacionseleccion", collection,
                Collection.class);                
                ordenacionSelecCount++;
            } catch (Exception e) {}
    
            try {
                classic = restTemplate.postForObject(classicsortUrl + "/classicsort", collection,
                Collection.class);                
                classicCount++;
            } catch (Exception e) {}
    
            try {
                merge = restTemplate.postForObject(mergesortUrl + "/mergesort", collection,
                Collection.class);                
                mergeCount++;
            } catch (Exception e) {}
            
            try {
                rawgapi = callAndAnalyzeRawgApi();                
                rawgapiCount++;
            } catch (Exception e) {}
        }
    
        // Calcular el resultado final de la votación
        Collection<Game> result = null;
        int maxCount = 0;
    
        if (bubbleCount > maxCount) {
            result = bubble;
            maxCount = bubbleCount;
        }
        if (ordenacionSelecCount > maxCount) {
            result = ordenacionSelec;
            maxCount = ordenacionSelecCount;
        }
        if (classicCount > maxCount) {
            result = classic;
            maxCount = classicCount;
        }
        if (mergeCount > maxCount) {
            result = merge;
            maxCount = mergeCount;
        }
        if (rawgapiCount > maxCount) {
            result = rawgapi;
            maxCount = rawgapiCount;
        }
    
        // Devolver el resultado final
        return result;
    }
    
}

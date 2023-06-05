package com.tfgmicroservices.orquestador.controller;

import java.util.Collection;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tfgmicroservices.orquestador.entities.Game;
import com.tfgmicroservices.orquestador.services.MicroserviceClient;

@RestController
@RequestMapping("/sort")
public class SortOrchestrationController {

    @Autowired
    private MicroserviceClient microserviceClient;

    @PostMapping
    public Map<String, Object> sort(@RequestBody Collection<Game> collection) {
        return microserviceClient.callVersions(collection);
    }
    
}

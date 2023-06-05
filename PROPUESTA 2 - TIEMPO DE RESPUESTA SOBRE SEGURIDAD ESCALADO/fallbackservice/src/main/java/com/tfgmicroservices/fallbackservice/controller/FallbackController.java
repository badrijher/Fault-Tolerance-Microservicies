package com.tfgmicroservices.fallbackservice.controller;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.tfgmicroservices.fallbackservice.entities.Game;
import com.tfgmicroservices.fallbackservice.services.FallbackService;

@RestController
@RequestMapping("/backup")
public class FallbackController {

    @Autowired
    private FallbackService fallbackService;

    @RequestMapping(value = "/{microserviceName}",method={RequestMethod.GET, RequestMethod.POST})
    public Collection<Game> backupAlgorithm(@PathVariable String microserviceName, @RequestBody Collection<Game> collection) {
        return fallbackService.backupAlgorithm(microserviceName, collection);
    }
    
}

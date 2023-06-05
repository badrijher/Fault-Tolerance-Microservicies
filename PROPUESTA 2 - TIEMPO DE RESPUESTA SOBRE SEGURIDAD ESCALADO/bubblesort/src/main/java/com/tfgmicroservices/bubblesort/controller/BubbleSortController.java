package com.tfgmicroservices.bubblesort.controller;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.tfgmicroservices.bubblesort.entities.Game;
import com.tfgmicroservices.bubblesort.services.BubbleSortService;

@RestController
public class BubbleSortController {

    @Autowired
    private BubbleSortService bubbleSortService;
 
    @RequestMapping(value = "/bubblesort", method = { RequestMethod.GET, RequestMethod.POST })
    public Collection<Game> bubbleSortVersion(@RequestBody Collection<Game> collection) {
        return bubbleSortService.bubbleSort(collection);
    }

}






package com.tfgmicroservices.mergesort.controller;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.tfgmicroservices.mergesort.entities.Game;
import com.tfgmicroservices.mergesort.services.MergeSortService;

@RestController
public class MergeSortController {

    @Autowired
    private MergeSortService mergeSortService;

    @RequestMapping(value = "/mergesort",method={RequestMethod.GET, RequestMethod.POST})
    public Collection<Game> mergeSortVersion(@RequestBody Collection<Game> collection){
        return mergeSortService.ordenarMergeSort(collection);
    }

    
}

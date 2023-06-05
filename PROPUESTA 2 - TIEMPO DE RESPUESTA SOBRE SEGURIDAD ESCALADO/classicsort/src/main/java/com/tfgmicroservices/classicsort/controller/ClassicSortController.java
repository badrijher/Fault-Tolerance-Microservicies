package com.tfgmicroservices.classicsort.controller;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.tfgmicroservices.classicsort.entities.Game;
import com.tfgmicroservices.classicsort.services.ClassicSortService;

@RestController
public class ClassicSortController {
    
    @Autowired    
    private ClassicSortService classicSortService;

    @RequestMapping(value = "/classicsort",method={RequestMethod.GET, RequestMethod.POST})
    public Collection<Game> classicSortVersion(@RequestBody Collection<Game> collection){
        return classicSortService.classicSortMod(collection);
    }
}

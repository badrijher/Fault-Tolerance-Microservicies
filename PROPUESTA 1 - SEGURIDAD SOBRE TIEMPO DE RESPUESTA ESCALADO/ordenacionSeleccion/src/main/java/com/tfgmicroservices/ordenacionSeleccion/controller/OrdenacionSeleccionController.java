package com.tfgmicroservices.ordenacionSeleccion.controller;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.tfgmicroservices.ordenacionSeleccion.entities.Game;
import com.tfgmicroservices.ordenacionSeleccion.services.OrdenacionSeleccionService;

@RestController
public class OrdenacionSeleccionController {

    @Autowired
    private OrdenacionSeleccionService ordenacionSeleccionService;

    @RequestMapping(value = "/ordenacionseleccion",method={RequestMethod.GET, RequestMethod.POST})
    public Collection<Game> ordenacionSeleccionVersion(@RequestBody Collection<Game> collection){
        return ordenacionSeleccionService.ordenacionSeleccion(collection);

    }

}

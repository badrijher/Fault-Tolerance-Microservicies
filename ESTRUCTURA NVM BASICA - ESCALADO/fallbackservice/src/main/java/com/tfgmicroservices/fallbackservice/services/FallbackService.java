package com.tfgmicroservices.fallbackservice.services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class FallbackService {

    // Implementar un algoritmo de backup que sea capaz de ordenar el arreglo en
    // caso de que todos los microservicios fallen.
    // Se utiliza un algoritmo de ordenamiento por inserción.
    public <T extends Comparable<T>> Collection<T> backupAlgorithm(String microserviceName, Collection<T> collection) {
        List<T> list = new ArrayList<>(collection);

        System.out.println("Error al llamar al microservicio " + microserviceName
                + ". Se está utilizando el algoritmo de backup para ordenar la colección.");

        final int n = list.size();
        for (int i = 1; i < n; ++i) {
            T key = list.get(i);
            int j = i - 1;

            while (j >= 0 && list.get(j).compareTo(key) > 0) {
                list.set(j + 1, list.get(j));
                j = j - 1;
            }
            list.set(j + 1, key);
        }

        return list;
    }  

}

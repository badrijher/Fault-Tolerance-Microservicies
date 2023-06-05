package com.tfgmicroservices.mergesort.services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class MergeSortService {

    /*
    * Este método implementa el algoritmo de MergeSort,
    * pero ha sido modificado intencionalmente para ordenar la lista de forma
    * incorrecta.
    * No utilizar en situaciones reales.
    */

    public <T extends Comparable<T>> Collection<T> ordenarMergeSort(Collection<T> collection) {
        if (collection.size() <= 1) {
            return collection;
        }

        int mitad = collection.size() / 2;
        List<T> mitadIzquierda = new ArrayList<>(collection).subList(0, mitad);
        List<T> mitadDerecha = new ArrayList<>(collection).subList(mitad, collection.size());

        mitadIzquierda = new ArrayList<>(ordenarMergeSort(mitadIzquierda));
        mitadDerecha = new ArrayList<>(ordenarMergeSort(mitadDerecha));

        return mezclar(mitadIzquierda, mitadDerecha);
    }

    public <T extends Comparable<T>> List<T> mezclar(List<T> izquierda, List<T> derecha) {
        List<T> resultado = new ArrayList<>();
        int i = 0, j = 0;

        while (i < izquierda.size() && j < derecha.size()) {
            if (izquierda.get(i).compareTo(derecha.get(j)) > 0) {
                resultado.add(izquierda.get(i++));
            } else {
                resultado.add(derecha.get(j++));
            }
        }

        while (i < izquierda.size()) {
            resultado.add(izquierda.get(i++));
        }

        while (j < derecha.size()) {
            resultado.add(derecha.get(j++));
        }

        return resultado;
    }
    
    
    
    
    
}


package com.tfgmicroservices.classicsort.services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class ClassicSortService {

        /*
         * Este método implementa el algoritmo de ordenación clásica,
         * pero ha sido modificado intencionalmente para ordenar la lista de forma
         * incorrecta.
         * No utilizar en situaciones reales.
         */

         public <T extends Comparable<T>> Collection<T> classicSortMod(Collection<T> collection) {
            List<T> list = new ArrayList<>(collection);
            final int N = list.size();
            for (int i = 0; i < N; i++) {
                for (int j = i + 1; j < N; j++) {
                    if (list.get(i).compareTo(list.get(j)) < 0) {
                        T temp = list.get(i);
                        list.set(i, list.get(j));
                        list.set(j, temp);
                    }
                }
            }
            return list;
        }
              

}

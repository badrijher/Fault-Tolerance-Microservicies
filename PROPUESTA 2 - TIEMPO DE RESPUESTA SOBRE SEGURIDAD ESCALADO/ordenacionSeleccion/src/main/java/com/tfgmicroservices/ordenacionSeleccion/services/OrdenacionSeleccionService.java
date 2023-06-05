package com.tfgmicroservices.ordenacionSeleccion.services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class OrdenacionSeleccionService {

        public <T extends Comparable<T>> Collection<T> ordenacionSeleccion(Collection<T> collection) {
                List<T> list = new ArrayList<>(collection);
                final int N = list.size();

                for (int i = 0; i < N; i++) {
                        int posMenor = i;
                        for (int j = i + 1; j < N; j++) {
                                if (list.get(j).compareTo(list.get(posMenor)) < 0) {
                                        posMenor = j;
                                }
                        }
                        if (posMenor != i) {
                                T temp = list.get(i);
                                list.set(i, list.get(posMenor));
                                list.set(posMenor, temp);
                        }
                }

                return list;
        }  

}

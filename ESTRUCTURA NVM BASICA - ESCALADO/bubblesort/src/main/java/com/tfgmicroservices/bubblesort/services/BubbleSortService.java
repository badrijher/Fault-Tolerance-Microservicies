package com.tfgmicroservices.bubblesort.services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class BubbleSortService {

    public <T extends Comparable<T>> Collection<T> bubbleSort(Collection<T> collection) {
        List<T> list = new ArrayList<>(collection);
        final int N = list.size();
        for (int i = N - 1; i > 0; i--) {
            for (int j = 0; j < i; j++) {
                if (list.get(j).compareTo(list.get(j + 1)) > 0) {
                    T temp = list.get(j);
                    list.set(j, list.get(j + 1));
                    list.set(j + 1, temp);
                }
            }
        }
        collection.clear();
        collection.addAll(list);
        return collection;
    }
    
 
}


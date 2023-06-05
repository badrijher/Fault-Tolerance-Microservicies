package com.tfgmicroservices.orquestador.services;

import java.util.Collection;

public interface SortService {
    
    <T extends Comparable<T>> Collection<T> sort(Collection<T> arr);
}

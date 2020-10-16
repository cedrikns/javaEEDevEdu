package ru.tsedrik.lesson10.hometask1;

import java.util.Collection;

public interface SortingMethods <V extends Comparable, T extends Collection<V>>{
    T bubbleSort(T list);
    T javaCollectionsSort(T list);
}

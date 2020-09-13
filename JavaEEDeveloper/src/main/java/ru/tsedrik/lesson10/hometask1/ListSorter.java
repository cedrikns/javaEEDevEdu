package ru.tsedrik.lesson10.hometask1;

import java.util.*;

public class ListSorter implements SortingMethods<List<Integer>>{

    public List<Integer> bubbleSort(List<Integer> list){
        if (list == null){
            throw new IllegalArgumentException("List can't be null");
        }

        Integer[] arr = new Integer[list.size()];
        list.toArray(arr);
        for (int i = 0; i < arr.length - 1; i++){
            for (int j = i + 1; j < arr.length; j++){
                if (arr[j] < arr[i]){
                    int tmp = arr[i];
                    arr[i] = arr[j];
                    arr[j] = tmp;
                }
            }
        }
        return Arrays.asList(arr);
    }

    public List<Integer> javaCollectionsSort(List<Integer> list){
        if (list == null){
            throw new IllegalArgumentException("List can't be null");
        }
        Collections.sort(list);
        return list;
    }
}

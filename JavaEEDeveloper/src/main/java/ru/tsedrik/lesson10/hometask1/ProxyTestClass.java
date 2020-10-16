package ru.tsedrik.lesson10.hometask1;

import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ProxyTestClass {
    public static void main(String[] args) {
        ListSorter sorter = new ListSorter();
        MethodsWorkTimeProxy handle = new MethodsWorkTimeProxy(sorter);

        SortingMethods<List<Integer>> listSorterProxy = (SortingMethods<List<Integer>>) Proxy.newProxyInstance(
                SortingMethods.class.getClassLoader(),
                sorter.getClass().getInterfaces(),
                handle);

        System.out.println("Test sorting methods from 10000 elements");

        for (int listSize = 10000, i = 0; i < 3; listSize *= 10, i++) {
            System.out.println("List size = " + listSize);
            for (int attemptNum = 0; attemptNum < 10; attemptNum++) {
                System.out.println("Attempt number " + (attemptNum + 1) + ":");
                List<Integer> list = new ArrayList<>();
                for (int num = 0; num < listSize; num++) {
                    list.add(new Random().nextInt(100000));
                }

                listSorterProxy.bubbleSort(list);
                listSorterProxy.javaCollectionsSort(list);
            }
        }

    }
 }

package ru.tsedrik.lesson4.exercise1;

import java.util.ArrayList;
import java.util.List;

public class Exercise1 {
    public static void main(String[] args) {
        List<Integer> ints = new ArrayList<Integer>();
        ints.add(1);
        ints.add(2);
        List<? extends Number> nums = ints;
//        nums.add(3.14);     //generic with ? extends .. is immutable

    }
}

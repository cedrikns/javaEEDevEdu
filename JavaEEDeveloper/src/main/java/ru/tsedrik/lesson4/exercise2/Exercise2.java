package ru.tsedrik.lesson4.exercise2;

import java.util.List;

public class Exercise2 {
//    public static <T> T getFirst(List<? super T> list) {
//        return list.get(0); // compile-time error
//    }

    //worked verion
    public static <T> Object getFirst(List<? super T> list) {
        return list.get(0); // compile-time error
    }
}

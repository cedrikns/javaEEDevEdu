package ru.tsedrik.lesson13.hometask1;

import java.util.ArrayList;
import java.util.List;

public class OOMEHeap {
    public static void main(String[] args) {
        List<TestClass> listOOME = new ArrayList<>();
        while (true){
            new TestClass(10000000);
            listOOME.add(new TestClass(10000000));
        }
    }

    static class TestClass{
        private Object[] arr;

        TestClass(int n){
            arr = new Object[n];
            for (int i = 0; i < n; i++){
                arr[i] = new Object();
            }
        }
    }
}

package ru.tsedrik.lesson3.hometask1;

public class ModelingNPE {
    public static void main(String[] args) {
        System.out.println("Hello, World!");
        Object o = null;
        o.toString();
    }
}

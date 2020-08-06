package ru.tsedrik.lesson3.hometask1;

import ru.tsedrik.lesson3.hometask1.exceptions.MyException;

public class ThrowingME {
    public static void main(String[] args) throws MyException{
        System.out.println("Hello, World!");
        throw new MyException();
    }
}

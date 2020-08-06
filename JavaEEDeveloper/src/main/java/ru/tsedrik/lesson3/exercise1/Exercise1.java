package ru.tsedrik.lesson3.exercise1;

import java.io.IOException;

class A{
    public A method() throws Throwable { // 1
        return new Single();
    }
}

class Single extends A{
    public Single method(String str) throws RuntimeException { // 2
        return new Single();
    }
    public Single method() throws IOException {  //3
        return new Double();
    }
}

class Double extends Single{
    public void method(Integer digit) throws ClassCastException {      // 4
    }
    public Double method() throws IOException {  // 5
        return new Double();
    }
}



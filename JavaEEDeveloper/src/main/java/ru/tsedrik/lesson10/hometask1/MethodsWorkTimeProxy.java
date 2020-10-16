package ru.tsedrik.lesson10.hometask1;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class MethodsWorkTimeProxy implements InvocationHandler {
    private Object proxiedClass;

    public MethodsWorkTimeProxy(Object proxiedClass) {
        this.proxiedClass = proxiedClass;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Long startTime = System.currentTimeMillis();
        Object result = method.invoke(proxiedClass, args);
        Long elapsedTime = System.currentTimeMillis() - startTime;
        System.out.println("Elapsed time of " + method.getName() + " method is " + elapsedTime + " ms.");
        try(BufferedWriter bw = new BufferedWriter(new FileWriter("SortingResults.txt", true))){
            bw.write("Elapsed time of " + method.getName() + " method is " + elapsedTime + " ms.");
            bw.newLine();
        }
        return result;
    }
}

package ru.tsedrik.lesson8.hometask1;

import java.math.BigInteger;
import java.util.*;
import java.util.concurrent.*;

public class ArrayNumberFactorials {

    public static List<BigInteger> getNumbersFactorialsWithoutExecutorService(Integer[] numbers){
        List<BigInteger> factorials = new ArrayList<>();

        long startTime = System.currentTimeMillis();
        ForkJoinPool pool = new ForkJoinPool();

        for (int i = 0; i < numbers.length; i++) {
            ForkJoinTask<BigInteger> task = new NumberFactorialTask(numbers[i]);
            BigInteger factorial = pool.invoke(task);
            factorials.add(i, factorial);
        }

        long endTime = System.currentTimeMillis();
        System.out.println("Время работы без ExecutorService = " + (endTime - startTime) + " milliseconds");

        return factorials;
    }

    public static Collection<BigInteger> getNumbersFactorialsWithFuture(Integer[] numbers){
        Map<Integer, BigInteger> factorials = new ConcurrentHashMap();
        ExecutorService service = null;
        long startTime = System.currentTimeMillis();
        try {
            service = Executors.newCachedThreadPool();
            ForkJoinPool pool = new ForkJoinPool();
            for (int i = 0; i < numbers.length; i++) {
                int curIdx = i;
                int curNum = numbers[curIdx];

                BigInteger result = service.submit(() -> {
                    ForkJoinTask<BigInteger> task = new NumberFactorialTask(curNum);
                    BigInteger factorial = pool.invoke(task);
                    return factorial;
                }).get();
                factorials.put(curIdx, result);
            }
            long endTime = System.currentTimeMillis();
            System.out.println("Время работы с ExecutorService with Future = " + (endTime - startTime) + " milliseconds");
        }catch (Exception e){
            e.printStackTrace();
        } finally {
            if (service != null) {
                service.shutdown();
            }
        }
        return factorials.values();
    }

    public static Collection<BigInteger> getNumbersFactorialsWithCompletableFuture(Integer[] numbers){
        Map<Integer, BigInteger> factorials = new ConcurrentHashMap();
        Set<String> threadNames = new CopyOnWriteArraySet<>();
        long startTime = System.currentTimeMillis();
        try {

            for (int i = 0; i < numbers.length; i++) {
                int curIdx = i;
                int curNum = numbers[curIdx];

                CompletableFuture.supplyAsync(() -> {
//                    System.out.println(Thread.currentThread().getName());
                    threadNames.add(Thread.currentThread().getName());
                    ForkJoinTask<BigInteger> task = new NumberFactorialTask(curNum);
                    BigInteger factorial =task.invoke();
                    return factorial;
                }).thenAccept(b -> {
                    factorials.put(curIdx, b);
                });
            }
            while (factorials.size() < numbers.length){}

            long endTime = System.currentTimeMillis();
            System.out.println("Время работы с CompletableFuture = " + (endTime - startTime) + " milliseconds");
            System.out.println("Количество уникальных потоков: " + threadNames.size());

        }catch (Exception e){
            e.printStackTrace();
        }

        return factorials.values();
    }

    public static void main(String[] args) {
//        Integer[] numbers = {5, 7, 30, 12, 1, 5, 11, 300, 115, 34};
//        Integer[] numbers = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};

        Integer[] numbers = new Integer[10000];
        for (int i = 0; i < 10000; i++) {
            numbers[i] = 100000 + i + 1;
        }

//        getNumbersFactorialsWithoutExecutorService(numbers);
//        getNumbersFactorialsWithFuture(numbers);
        getNumbersFactorialsWithCompletableFuture(numbers);

    }
}

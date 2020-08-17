package ru.tsedrik.lesson8.hometask1;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
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

    public static List<BigInteger> getNumbersFactorials(Integer[] numbers){
        List<BigInteger> factorials = new ArrayList<>();
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
//                    System.out.println(curNum + "! = " + factorial);
                    return factorial;
                }).get();
                factorials.add(curIdx, result);
            }
            long endTime = System.currentTimeMillis();
            System.out.println("Время работы с ExecutorService = " + (endTime - startTime) + " milliseconds");
        }catch (InterruptedException | ExecutionException e){
            e.printStackTrace();
        } finally {
            if (service != null) {
                service.shutdown();
            }
        }
        return factorials;
    }

    public static void main(String[] args) {
        Integer[] numbers = {5, 7, 30, 12, 1, 5, 11, 300, 115, 34};
//        Integer[] numbers = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};

//        Integer[] numbers = new Integer[1000];
//        for (int i = 0; i < 1000; i++) {
//            numbers[i] = i + 1;
//        }

        System.out.println(getNumbersFactorialsWithoutExecutorService(numbers));
        System.out.println(getNumbersFactorials(numbers));

    }
}

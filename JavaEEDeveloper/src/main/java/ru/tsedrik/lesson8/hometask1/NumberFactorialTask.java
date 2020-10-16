package ru.tsedrik.lesson8.hometask1;

import java.math.BigInteger;
import java.util.Comparator;
import java.util.Map;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.RecursiveTask;

public class NumberFactorialTask extends RecursiveTask<BigInteger> {
    private Integer startNum;
    private Integer endNum;
    private boolean isMainObj;
    private BigInteger initialValue = BigInteger.ONE;

    public static Map<Integer, BigInteger> calculatedFactorials = new ConcurrentSkipListMap<>(Comparator.reverseOrder());
    static {
        calculatedFactorials.put(0, BigInteger.ONE);
        calculatedFactorials.put(1, BigInteger.ONE);
    }

    public NumberFactorialTask(int num) {
        if (calculatedFactorials.containsKey(num)) {
            startNum = endNum = 1;
            initialValue = calculatedFactorials.get(num);
        } else {
            int maxValue = calculatedFactorials.keySet().stream().filter(x -> x < num).findFirst().get();
            if (maxValue > 1){
                initialValue = calculatedFactorials.get(maxValue);
                startNum = maxValue + 1;
            } else {
                startNum = 1;
            }
            endNum = num;
        }
        isMainObj = true;
    }

    private NumberFactorialTask(int startNum, int endNum){
        this.startNum = startNum;
        this.endNum = endNum;
        isMainObj = false;
    }

    @Override
    protected BigInteger compute() {
        Integer diff = endNum - startNum;
        BigInteger result;
        if (diff == 1){
            result = new BigInteger(endNum.toString()).multiply(new BigInteger(startNum.toString()));
        } else if (diff == 0) {
            result = new BigInteger(startNum.toString());
        } else {
            Integer middle = (startNum + endNum) / 2;
            RecursiveTask<BigInteger> otherTask = new NumberFactorialTask(startNum,middle);
            otherTask.fork();
            result = new NumberFactorialTask(middle + 1, endNum).compute().multiply(otherTask.join());
        }

        if (isMainObj){
            result = initialValue.multiply(result);
            calculatedFactorials.putIfAbsent(endNum, result);
       }
        return result;
    }
}

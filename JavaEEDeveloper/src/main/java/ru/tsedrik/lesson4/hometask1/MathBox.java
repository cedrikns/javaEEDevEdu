package ru.tsedrik.lesson4.hometask1;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashSet;
import java.util.Iterator;

public class MathBox<T extends Number> extends ObjectBox<T>{

    public MathBox (T[] numbers){
        for (int i = 0; i < numbers.length; i++){
            T curNum = numbers[i];
            if (curNum != null) {
                if (objects.contains(curNum)){
                    throw new DublicatedNumberException();
                }
                this.objects.add(curNum);
            }
        }
    }

    public T summator(){
        Number sum = 0;
        for (Object num : objects){
            sum = sumTwoNums((T)sum, (T) num);
        }
        return (T)sum;
    }

    private Number sumTwoNums(T n1, T n2){
        if ((n1 instanceof BigDecimal) || (n2 instanceof BigDecimal) || (n1 instanceof BigInteger) || (n2 instanceof BigInteger)){
            return new BigDecimal(n1.toString()).add(new BigDecimal(n2.toString()));
        } else if (((n1 instanceof Double) || (n2 instanceof Double)) || ((n1 instanceof Float) || (n2 instanceof Float))){
            return (Double)(n1.doubleValue() + n2.doubleValue());
        } else if ((n1 instanceof Long) || (n2 instanceof Long)){
            return n1.longValue() + n2.longValue();
        } else {
            return n1.intValue() + n2.intValue();
        }
    }

    public void splitter(T divider){
        HashSet<T> set = new HashSet<>();
        for (T num: objects){
            set.add((T)divideTwoNums(divider, num));
        }
        objects = set;
    }

    private Number divideTwoNums(T divider, T num){
        if ((divider instanceof BigDecimal) || (num instanceof BigDecimal)
                || (divider instanceof  BigInteger) || (num instanceof BigInteger)){
            return new BigDecimal(num.toString()).divide(new BigDecimal(divider.toString()));
        } else {
            return num.doubleValue() / divider.doubleValue();
        }
    }

    public void deleteNum(Integer num){
        Iterator<T> iterator = objects.iterator();
        while (iterator.hasNext()){
            Number curNum = iterator.next();
            if (curNum.toString().equals(num.toString())){
                iterator.remove();
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MathBox)) return false;
        MathBox mathBox = (MathBox) o;
        return objects.equals(mathBox.objects);
    }

    @Override
    public int hashCode() {
        return objects.hashCode();
    }

    @Override
    public String toString() {
        return "MathBox " + objects ;
    }
}

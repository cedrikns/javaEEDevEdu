package ru.tsedrik.lesson3.hometask2;

import java.util.Random;

public class SqrtAndPow {
    public static void main(String[] args) {
        int n;

        if (args.length > 0){
            try {
                n = Integer.parseInt(args[0]);
            } catch (NumberFormatException e){
                e.printStackTrace();
                return;
            }
        } else {
            n = 10;
        }

        if (n < 0){
            throw new IllegalArgumentException("negative number");
        }

        Random random = new Random();

        for (int i = 0; i < n; i++){
            int k = random.nextInt(Integer.MAX_VALUE);
            if (k < 0){
                throw new IllegalArgumentException("negative number");
            }
            double q = Math.sqrt(k);
            int qInt = (int)q;
            if (qInt * qInt == k){
                System.out.println(k);
            }
       }
    }
}

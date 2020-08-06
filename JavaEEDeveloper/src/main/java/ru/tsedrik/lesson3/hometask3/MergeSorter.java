package ru.tsedrik.lesson3.hometask3;

public class MergeSorter implements Sorter {

    @Override
    public Person[] sort(Person[] people){
        sort(people, 0, people.length - 1);
        return people;
    }

    private void sort(Person[] array, int left, int right){
        if (left < right){
            int middle = (left + right) / 2;

            sort(array, left, middle);
            sort(array, middle + 1, right);
            merge(array, left, middle, right);
        }
    }

    private void merge(Person[] array, int left, int middle, int right){
        int leftArrLength = middle - left + 1;
        int rightArrLength = right - middle;

        Person[] leftArr = new Person[leftArrLength];
        Person[] rightArr = new Person[rightArrLength];

        for (int i = 0; i < leftArrLength; i++){
            leftArr[i] = array[left + i];
        }
        for (int j = 0; j < rightArrLength; j++){
            rightArr[j] = array[middle + 1 + j];
        }

        int leftArrIdx = 0, rightArrIdx = 0;

        int resultArrIdx = left;
        while (leftArrIdx < leftArrLength && rightArrIdx < rightArrLength){
            if (leftArr[leftArrIdx].compareTo(rightArr[rightArrIdx]) <= 0){
                array[resultArrIdx] = leftArr[leftArrIdx];
                leftArrIdx++;
            } else {
                array[resultArrIdx] = rightArr[rightArrIdx];
                rightArrIdx++;
            }
            resultArrIdx++;
        }

        while (leftArrIdx < leftArrLength){
            array[resultArrIdx] = leftArr[leftArrIdx];
            leftArrIdx++;
            resultArrIdx++;
        }

        while (rightArrIdx < rightArrLength){
            array[resultArrIdx] = rightArr[rightArrIdx];
            rightArrIdx++;
            resultArrIdx++;
        }
    }
}

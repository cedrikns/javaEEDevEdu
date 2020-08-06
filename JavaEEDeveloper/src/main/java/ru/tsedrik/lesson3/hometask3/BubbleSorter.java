package ru.tsedrik.lesson3.hometask3;

public class BubbleSorter implements Sorter {

    @Override
    public Person[] sort(Person[] people){
        boolean isContinue = true;
        while (isContinue) {
            isContinue = false;
            for (int i = 1; i < people.length; i++) {
                if (people[i].compareTo(people[i - 1]) < 0) {
                    Person tmpPerson = people[i];
                    people[i] = people[i-1];
                    people[i-1] = tmpPerson;
                    isContinue = true;
                }
            }
        }
        return people;
    }
}

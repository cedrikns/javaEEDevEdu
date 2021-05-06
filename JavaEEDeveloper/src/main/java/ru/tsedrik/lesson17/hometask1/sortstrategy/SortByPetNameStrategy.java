package ru.tsedrik.lesson17.hometask1.sortstrategy;

import ru.tsedrik.lesson17.hometask1.Pet;

import java.util.Collection;
import java.util.Comparator;
import java.util.stream.Collectors;

public class SortByPetNameStrategy<T extends Pet> implements SortStrategy<T>{
    public Collection<T> sort(Collection<T> collection){
        Comparator<Pet> comparator = Comparator.comparing(Pet::getName);

        return collection.stream().sorted(comparator).collect(Collectors.toList());
    }
}

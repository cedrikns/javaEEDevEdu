package ru.tsedrik.lesson17.hometask1.sortstrategy;

import ru.tsedrik.lesson17.hometask1.Pet;

import java.util.Collection;
import java.util.Comparator;
import java.util.stream.Collectors;

public class SortByPetOwnerStrategy<T extends Pet> implements SortStrategy<T>{

    public Collection<T> sort(Collection<T> collection){
        Comparator<Pet> comparator = (o1, o2) -> {
            if ((o1.getOwner() == null) && (o2.getOwner() == null)){
                return 0;
            }
            if (o1.getOwner() == null) {
                return 1;
            }
            if (o2.getOwner() == null){
                return -1;
            }
           return o1.getOwner().compareTo(o2.getOwner());
       };

       return collection.stream().sorted(comparator).collect(Collectors.toList());
    }
}

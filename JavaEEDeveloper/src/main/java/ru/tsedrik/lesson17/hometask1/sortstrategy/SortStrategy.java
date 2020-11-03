package ru.tsedrik.lesson17.hometask1.sortstrategy;

import ru.tsedrik.lesson17.hometask1.Pet;

import java.util.Collection;

public interface SortStrategy <T extends Pet> {
    Collection<T> sort(Collection<T> collection);
}

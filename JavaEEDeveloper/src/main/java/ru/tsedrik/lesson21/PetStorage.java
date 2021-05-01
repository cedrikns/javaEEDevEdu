package ru.tsedrik.lesson21;

import java.util.Collection;
import java.util.UUID;

public interface PetStorage {
    boolean add(Pet pet);
    Pet search(UUID id);
    Pet change(Pet pet);
    Collection<Pet> getAll();
    boolean delete(Pet pet);
}

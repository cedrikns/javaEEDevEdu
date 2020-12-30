package ru.tsedrik.lesson21;

import ru.tsedrik.lesson21.exceptions.DublicatePetException;
import ru.tsedrik.lesson21.exceptions.PetNotFoundException;

import java.util.*;

public class PetsCardFile{

    private PetStorage storage;
    public PetsCardFile(PetStorage storage){
        this.storage = storage;
    }

    public boolean add(Pet newPet){
        if (storage.search(newPet.getId()) != null){
            throw new DublicatePetException();
        }
        return storage.add(newPet);
    }

    public Pet search(UUID id){
        return storage.search(id);
    }

    public Pet change(Pet tmpPet){
        UUID id = tmpPet.getId();
        if (storage.search(id) == null) {
            throw new PetNotFoundException("There was not found pet with id = " + id + " in the card-file");
        }

        return storage.change(tmpPet);
    }

    public Collection<Pet> getAll() {
        return storage.getAll();
    }

    public boolean delete(Pet pet){
        return storage.delete(pet);
    }

    public void print() {
        for (Pet p : storage.getAll()){
            System.out.println(p);
        }
    }

    public void printPets(Comparator<Pet> comparator){
        List<Pet> sortedPets = sortPets(comparator);
        for (Pet pet : sortedPets){
            System.out.println(pet);
        }
    }

    public List<Pet> sortPets(Comparator<Pet> comparator){
        ArrayList<Pet> petsList = new ArrayList<>(storage.getAll());
        Collections.sort(petsList, comparator);
        return petsList;
    }
}

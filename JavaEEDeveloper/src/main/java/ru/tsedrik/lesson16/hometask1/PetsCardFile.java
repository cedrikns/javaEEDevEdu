package ru.tsedrik.lesson16.hometask1;

import ru.tsedrik.lesson16.hometask1.exceptions.DublicatePetException;
import ru.tsedrik.lesson16.hometask1.exceptions.PetNotFoundException;

import java.util.*;

public class PetsCardFile extends CardFile<UUID, Pet>{

    public PetsCardFile(Storage storage){
        super(storage);
    }

    public Pet add(Pet newPet){
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

    public Pet delete(Pet pet){
        return storage.delete(pet);
    }

    @Override
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

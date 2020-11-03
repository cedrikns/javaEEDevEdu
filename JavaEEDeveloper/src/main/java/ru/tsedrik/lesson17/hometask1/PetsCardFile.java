package ru.tsedrik.lesson17.hometask1;

import ru.tsedrik.lesson17.hometask1.exceptions.DublicatePetException;
import ru.tsedrik.lesson17.hometask1.exceptions.PetNotFoundException;
import ru.tsedrik.lesson17.hometask1.sortstrategy.SortStrategy;

import java.util.*;

public class PetsCardFile extends CardFile<UUID, Pet>{

    private SortStrategy<Pet> sortStrategy;

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
        Collection<Pet> collection = storage.getAll();
        for (Pet p : collection){
            System.out.println(p);
        }
    }

    public void sortedPrint(){
        Collection<Pet> collection =sortStrategy.sort(storage.getAll());
        sortStrategy.sort(collection);
        for (Pet pet : collection){
            System.out.println(pet);
        }
    }

    public List<Pet> sortPets(Comparator<Pet> comparator){
        ArrayList<Pet> petsList = new ArrayList<>(storage.getAll());
        Collections.sort(petsList, comparator);
        return petsList;
    }

    public void setSortStrategy(SortStrategy<Pet> sortStrategy) {
        this.sortStrategy = sortStrategy;
    }
}

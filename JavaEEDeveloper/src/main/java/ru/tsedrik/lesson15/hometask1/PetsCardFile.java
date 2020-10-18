package ru.tsedrik.lesson15.hometask1;



import ru.tsedrik.lesson15.hometask1.exceptions.DublicatePetException;
import ru.tsedrik.lesson15.hometask1.exceptions.PetNotFoundException;

import java.util.*;

public class PetsCardFile {
    private static PetsCardFile instance;
    private Map<UUID, Pet> pets = new HashMap<>();
    private Map<String, List<Pet>> petsByName = new HashMap<>();

    private PetsCardFile(){}

    public static PetsCardFile getInstance(){
        if (instance == null){
            instance = new PetsCardFile();
        }
        return instance;
    }

    public void addPet(Pet newPet){
        if (pets.containsKey(newPet.getId())){
            throw new DublicatePetException();
        }
        pets.put(newPet.getId(), newPet);

        String petName = newPet.getName();
        petsByName.computeIfAbsent(petName, key -> new ArrayList<>()).add(newPet);
    }

    public List<Pet> searchPet(String name){
        return petsByName.get(name);
    }

    public void changePet(UUID id, Pet tmpPet){
        if (!pets.containsKey(id)) {
            throw new PetNotFoundException("There was not found pet with id = " + id + " in the card-file");
        }

        Pet curPet = pets.get(id);
        curPet.setName(tmpPet.getName());
        curPet.setOwner(tmpPet.getOwner());
    }

    public void printPets(Comparator<Pet> comparator){
        List<Pet> sortedPets = sortPets(comparator);
        for (Pet pet : sortedPets){
            System.out.println(pet);
        }
    }

    public List<Pet> sortPets(Comparator<Pet> comparator){
        ArrayList<Pet> petsList = new ArrayList<>(pets.values());
        Collections.sort(petsList, comparator);
        return petsList;
    }

    public Map<UUID, Pet> getPets() {
        return new HashMap<>(pets);
    }
}

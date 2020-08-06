package ru.tsedrik.lesson5.hometask1;

import ru.tsedrik.lesson5.hometask1.exceptions.DublicatePetException;

import java.util.*;
import java.util.function.Consumer;

public class PetsCardFile {
    public HashMap<UUID, Pet> pets = new HashMap<>();
    public HashMap<String, ArrayList<Pet>> petsByName = new HashMap<>();

    public void addPet(String name, Person owner, double weight){
        Pet newPet = new Pet(name, owner, weight);
        addPet(newPet);
    }

    public void addPet(Pet newPet){
        if (pets.containsKey(newPet.getId())){
            throw new DublicatePetException();
        }
        pets.put(newPet.getId(), newPet);
        String petName = newPet.getName();
        if (petsByName.containsKey(petName)){
            petsByName.get(petName).add(newPet);
        } else {
            ArrayList<Pet> list = new ArrayList<>();
            list.add(newPet);
            petsByName.put(petName, list);
        }
    }

    public ArrayList<Pet> searchPet(String name){
        return petsByName.get(name);
    }

    public void changePetData(UUID id, String newName){
        if (pets.containsKey(id)) {
            Pet curPet = pets.get(id);
            curPet.setName(newName);
        } else {
            System.out.println("There was not found pet with id = " + id + " in the card-file");
        }
    }


    public void changePetData(UUID id, double newWeight){
        if (pets.containsKey(id)) {
            Pet curPet = pets.get(id);
            curPet.setWeight(newWeight);
        } else {
            System.out.println("There was not found pet with id = " + id + " in the card-file");
        }
    }

    public void changePetData(UUID id, Person newOwner){
        if (pets.containsKey(id)) {
            Pet curPet = pets.get(id);
            curPet.setOwner(newOwner);
        } else {
            System.out.println("There was not found pet with id = " + id + " in the card-file");
        }
    }

    public void changePetData(UUID id, Pet tmpPet){
        if (pets.containsKey(id)) {
            Pet curPet = pets.get(id);
            curPet.setName(tmpPet.getName());
            curPet.setWeight(tmpPet.getWeight());
            curPet.setOwner(tmpPet.getOwner());
        } else {
            System.out.println("There was not found pet with id = " + id + " in the card-file");
        }
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

}

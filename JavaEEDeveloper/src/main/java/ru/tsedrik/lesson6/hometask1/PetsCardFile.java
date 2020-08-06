package ru.tsedrik.lesson6.hometask1;

import ru.tsedrik.lesson6.hometask1.exceptions.DublicatePetException;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class PetsCardFile {
    public HashMap<UUID, Pet> pets = new HashMap<>();

    public void addPet(String name, Person owner, double weight){
        Pet newPet = new Pet(name, owner, weight);
        addPet(newPet);
    }

    public void addPet(Pet newPet){
        if (pets.containsKey(newPet.getId())){
            throw new DublicatePetException();
        }
        pets.put(newPet.getId(), newPet);
    }

    public List<Pet> searchPet(String name){
        List<Pet> petsWithName = pets.values().stream().filter(p -> p.getName().equals(name)).collect(Collectors.toList());
        return petsWithName;
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
        pets.values().stream().sorted(comparator).forEach(System.out::println);
    }

}

package ru.tsedrik.lesson5.hometask1;

import java.util.Comparator;

public class WorkWithPetsCardFile {
    public static void main(String[] args) {

        PetsCardFile petsCardFile = new PetsCardFile();

        for (int i = 0; i < 10; i++){
            petsCardFile.addPet(DataGenerationUtil.generatePet());
        }

        System.out.println(petsCardFile.getPets());

        Comparator<Pet> comparator = new Comparator<Pet>() {
            @Override
            public int compare(Pet o1, Pet o2) {
                if (o1.getOwner() != null && o2.getOwner() != null) {
                    int ownerCompareResult = o1.getOwner().compareTo(o2.getOwner());
                    if (ownerCompareResult != 0) {
                        return ownerCompareResult;
                    }
                }
                int nameCompareResult = o1.getName().compareTo(o2.getName());
                if (nameCompareResult != 0){
                    return nameCompareResult;
                }
                return Double.compare(o1.getWeight(), o2.getWeight());
            }
        };

        petsCardFile.printPets(comparator);
    }
}

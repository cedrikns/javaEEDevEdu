package ru.tsedrik.lesson17.hometask1;

import ru.tsedrik.lesson17.hometask1.sortstrategy.SortByPetNameStrategy;
import ru.tsedrik.lesson17.hometask1.sortstrategy.SortByPetOwnerStrategy;
import ru.tsedrik.lesson17.hometask1.sortstrategy.SortStrategy;

import java.time.LocalDate;

public class WorkWithPetsCardFile {
    public static void main(String[] args) {

        PetsCardFile petsCardFile = new PetsCardFile(new MapStorage());

        for (int i = 0; i < 5; i++){
            petsCardFile.add(DataGenerationUtil.generatePet());
        }
       Pet newPet = new Pet.PetBuilder("cat", "Мурзик")
                .setBirthday(LocalDate.of(2018, 12, 04))
                .setBreed("Русская голубая")
                .setCountryOfBirth("Россия")
                .build();

        petsCardFile.add(newPet);
        petsCardFile.print();
        System.out.println();

        SortStrategy<Pet> sortStrategy = new SortByPetNameStrategy<>();
        petsCardFile.setSortStrategy(sortStrategy);
        petsCardFile.sortedPrint();
        System.out.println();

        sortStrategy = new SortByPetOwnerStrategy<>();
        petsCardFile.setSortStrategy(sortStrategy);
        petsCardFile.sortedPrint();
        System.out.println();

    }
}

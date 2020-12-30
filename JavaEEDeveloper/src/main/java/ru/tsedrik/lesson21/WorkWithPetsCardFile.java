package ru.tsedrik.lesson21;

import java.time.LocalDate;

public class WorkWithPetsCardFile {
    public static void main(String[] args) throws Exception{

        PetStorage storage = new FileStorage("storage");
        PetsCardFile cardFile = new PetsCardFile(storage);

        for (int i = 0; i < 5; i++){
            cardFile.add(DataGenerationUtil.generatePet());
        }

       Pet newPet = new Pet.PetBuilder("cat", "Мурзик")
                .setBirthday(LocalDate.of(2018, 12, 04))
                .setBreed("Русская голубая")
                .setCountryOfBirth("Россия")
                .build();

        System.out.println(newPet);

        cardFile.add(newPet);

        cardFile.print();

        System.out.println("Питомец с Id = " + newPet.getId() + ":");
        System.out.println(cardFile.search(newPet.getId()));
    }
}

package ru.tsedrik.lesson16.hometask1;

import java.time.LocalDate;
import java.util.UUID;

public class WorkWithPetsCardFile {
    public static void main(String[] args) {

        CardFile<UUID, Pet> cardFile = new PetsCardFile(new MapStorage());

        for (int i = 0; i < 10; i++){
            cardFile.add(DataGenerationUtil.generatePet());
        }
       Pet newPet = new Pet.PetBuilder("cat", "Мурзик")
                .setBirthday(LocalDate.of(2018, 12, 04))
                .setBreed("Русская голубая")
                .setCountryOfBirth("Россия")
                .build();

        cardFile.add(newPet);

        cardFile.print();
    }
}

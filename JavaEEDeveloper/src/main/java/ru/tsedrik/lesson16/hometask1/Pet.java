package ru.tsedrik.lesson16.hometask1;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Objects;
import java.util.UUID;

public class Pet implements Identifiable<UUID>{

    private UUID id;
    private String petType;
    private String breed;
    private String countryOfBirth;
    private LocalDate birthday;
    private Pet mother;
    private Pet father;
    private String[] titles;
    private String name;
    private Person owner;

    private Pet(PetBuilder petBuilder) {
        this.id = petBuilder.id;
        this.name = petBuilder.name;
        this.petType = petBuilder.petType;
        this.breed = petBuilder.breed;
        this.countryOfBirth = petBuilder.countryOfBirth;
        this.birthday = petBuilder.birthday;
        this.mother = petBuilder.mother;
        this.father = petBuilder.father;
        this.titles = petBuilder.titles;
        this.owner = petBuilder.owner;
    }

    public static class PetBuilder{
        private UUID id;
        private String petType;
        private String breed;
        private String countryOfBirth;
        private LocalDate birthday;
        private Pet mother;
        private Pet father;
        private String[] titles;
        private String name;
        private Person owner;

        public PetBuilder(String petType, String name){
            this.id = UUID.randomUUID();
            this.petType = petType;
            this.name = name;
        }

        public PetBuilder setCountryOfBirth(String countryOfBirth){
            this.countryOfBirth = countryOfBirth;
            return this;
        }

        public PetBuilder setBirthday(LocalDate birthday){
            this.birthday = birthday;
            return this;
        }
        public PetBuilder setMother(Pet mother){
            this.mother = mother;
            return this;
        }
        public PetBuilder setFather(Pet father){
            this.father = father;
            return this;
        }
        public PetBuilder setTitles(String[] titles){
            this.titles = titles;
            return this;
        }
        public PetBuilder setOwner(Person owner){
            this.owner = owner;
            return this;
        }
        public PetBuilder setBreed(String breed){
            this.breed = breed;
            return this;
        }

        public Pet build(){
            return new Pet(this);
        }
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Person getOwner() {
        return owner;
    }

    public void setOwner(Person owner) {
        this.owner = owner;
    }

    public String getPetType() {
        return petType;
    }

    public void setPetType(String petType) {
        this.petType = petType;
    }

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public String getCountryOfBirth() {
        return countryOfBirth;
    }

    public void setCountryOfBirth(String countryOfBirth) {
        this.countryOfBirth = countryOfBirth;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public Pet getMother() {
        return mother;
    }

    public void setMother(Pet mother) {
        this.mother = mother;
    }

    public Pet getFather() {
        return father;
    }

    public void setFather(Pet father) {
        this.father = father;
    }

    public String[] getTitles() {
        return titles;
    }

    public void setTitles(String[] titles) {
        this.titles = titles;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Pet)) return false;
        Pet pet = (Pet) o;
        return Objects.equals(id, pet.id) &&
                Objects.equals(petType, pet.petType) &&
                Objects.equals(breed, pet.breed) &&
                Objects.equals(countryOfBirth, pet.countryOfBirth) &&
                Objects.equals(birthday, pet.birthday) &&
                Objects.equals(mother, pet.mother) &&
                Objects.equals(father, pet.father) &&
                Arrays.equals(titles, pet.titles) &&
                Objects.equals(name, pet.name) &&
                Objects.equals(owner, pet.owner);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, petType, breed, countryOfBirth, birthday);
    }

    @Override
    public String toString() {
        return "Pet{" +
                "petType='" + petType + '\'' +
                ", breed='" + breed + '\'' +
                ", countryOfBirth='" + countryOfBirth + '\'' +
                ", birthday=" + birthday +
                ", titles=" + Arrays.toString(titles) +
                ", name='" + name + '\'' +
                '}';
    }

}

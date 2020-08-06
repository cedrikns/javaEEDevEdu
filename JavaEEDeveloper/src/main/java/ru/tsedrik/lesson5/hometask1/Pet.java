package ru.tsedrik.lesson5.hometask1;

import java.text.DecimalFormat;
import java.util.UUID;

public class Pet implements Comparable<Pet>{

    private UUID id;
    private String name;
    private Person owner;
    private double weight;

    public Pet(String name, Person owner, double weight) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.owner = owner;
        this.weight = weight;
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

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Pet)) return false;
        Pet pet = (Pet) o;
        return id.equals(pet.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        return "Pet{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", owner=" + owner +
                ", weight=" + new DecimalFormat("#.#").format(weight) +
                '}';
    }

    @Override
    public int compareTo(Pet o) {
        int ownerCompareResult = owner.compareTo(o.owner);
        if (ownerCompareResult != 0){
            return ownerCompareResult;
        }
        int nameCompareResult = name.compareTo(o.name);
        if (nameCompareResult != 0){
            return nameCompareResult;
        }
        return Double.compare(weight, o.weight);
    }
}

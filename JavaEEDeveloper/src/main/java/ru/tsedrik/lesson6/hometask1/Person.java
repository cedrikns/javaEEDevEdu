package ru.tsedrik.lesson6.hometask1;

public class Person implements Comparable<Person>{
    private int age;
    private Sex sex;
    private String name;

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        if ((age >= 0) && (age <= 100)) {
            this.age = age;
        } else {
            throw new IllegalArgumentException();
        }
    }

    public Sex getSex() {
        return sex;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Person(int age, Sex sex, String name) {
        this.age = age;
        this.sex = sex;
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Person)) return false;
        Person person = (Person) o;
        return age == person.age &&
                name.equals(person.name);
    }

    @Override
    public int hashCode() {
        return age + 3 * name.hashCode();
    }

    @Override
    public String toString() {
        return "Person{" +
                "sex=" + sex +
                ", age=" + age +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public int compareTo(Person o) {
        int sexCompareResult = sex.ordinal() - o.sex.ordinal();
        if (sexCompareResult != 0){
            return sexCompareResult;
        }
        int ageCompareResult = o.age - age;
        if (ageCompareResult != 0){
            return ageCompareResult;
        }
        return name.compareTo(o.name);
    }
}

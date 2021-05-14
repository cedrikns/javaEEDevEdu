package ru.tsedrik.entity;

import java.util.UUID;

public class Student  implements Identifired<UUID> {

    /**
     * Идентификатор участника
     */
    private UUID id;

    /**
     * Электронный адрес участника
     */
    private String email;

    /**
     * Имя участника
     */
    private String firstName;

    /**
     * Фамилия участника
     */
    private String lastName;

    public Student(){}

    public Student(UUID id, String email) {
        this.id = id;
        this.email = email;
    }

    public Student(UUID id, String firstName, String lastName, String email) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email.toLowerCase();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email.toLowerCase();
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }
}

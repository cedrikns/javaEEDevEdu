package ru.tsedrik.entity;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "student")
public class Student  implements Identifired<UUID> {

    /**
     * Идентификатор участника
     */
    @Id
    @Column(name = "id")
    private UUID id;

    /**
     * Электронный адрес участника
     */
    @Column(nullable = false, unique = true)
    private String email;

    /**
     * Имя участника
     */
    @Column(name = "first_name")
    private String firstName;

    /**
     * Фамилия участника
     */
    @Column(name = "last_name")
    private String lastName;

    /**
     * Список курсов, на которые записался студент
     */
    @ManyToMany(cascade={CascadeType.PERSIST}, fetch = FetchType.LAZY)
    @JoinTable(name="course_student", joinColumns=@JoinColumn(name="student_id"), inverseJoinColumns=@JoinColumn(name="course_id"))
    private Set<Course> courses;

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

    public Set<Course> getCourses() {
        return courses;
    }

    public void setCourses(Set<Course> courses) {
        this.courses = courses;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Student student = (Student) o;

        if (!id.equals(student.id)) return false;
        if (!email.equals(student.email)) return false;
        if (!Objects.equals(firstName, student.firstName)) return false;
        return Objects.equals(lastName, student.lastName);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + email.hashCode();
        return result;
    }
}

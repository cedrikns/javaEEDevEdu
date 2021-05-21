package ru.tsedrik.entity;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "course")
public class Course implements Identifired<UUID>{

    /**
     * Идентификатор курса
     */
    @Id
    @Column(name = "id")
    private UUID id;

    /**
     * Тип курса, от которого зависит программа
     */
    @Column(name = "course_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private CourseType courseType;

    /**
     * Дата начала курса
     */
    @Column(name = "begin_date", nullable = false)
    private LocalDate beginDate;

    /**
     * Дата окончания курса
     */
    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

    /**
     * Количество групп на курсе
     */
    @Column(name = "max_students_count", nullable = false)
    private int maxStudentsCount;

    /**
     * Список участников, которые будут обучатья на курсе
     */
    @ManyToMany(cascade={CascadeType.PERSIST}, fetch = FetchType.LAZY)
    @JoinTable(name="course_student", joinColumns=@JoinColumn(name="course_id"), inverseJoinColumns=@JoinColumn(name="student_id"))
    private Set<Student> students;

    /**
     * Статус курса, от которого зависит можно ли на него записаться
     */
    @Column(name = "course_status", nullable = false)
    @Enumerated(EnumType.STRING)
    private CourseStatus courseStatus;

    public Course(){}

    public Course(UUID id, CourseType courseType, LocalDate beginDate, LocalDate endDate, int maxStudentsCount, CourseStatus courseStatus) {
        this.id = id;
        this.courseType = courseType;
        this.beginDate = beginDate;
        this.endDate = endDate;
        this.maxStudentsCount = maxStudentsCount;
        this.students = new HashSet<>();
        this.courseStatus = courseStatus;
    }

    @Override
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public CourseType getCourseType() {
        return courseType;
    }

    public void setCourseType(CourseType courseType) {
        this.courseType = courseType;
    }

    public LocalDate getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(LocalDate beginDate) {
        this.beginDate = beginDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public int getMaxStudentsCount() {
        return maxStudentsCount;
    }

    public void setMaxStudentsCount(int maxStudentsCount) {
        this.maxStudentsCount = maxStudentsCount;
    }

    public Set<Student> getStudents() {
        return students;
    }

    public void setStudents(Set<Student> students) {
        this.students = students;
    }

    public CourseStatus getCourseStatus() {
        return courseStatus;
    }

    public void setCourseStatus(CourseStatus courseStatus) {
        this.courseStatus = courseStatus;
    }

    @Override
    public String toString() {
        return "Course{" +
                "id=" + id +
                ", courseType=" + courseType +
                ", beginDate=" + beginDate +
                ", endDate=" + endDate +
                ", maxStudentCount=" + maxStudentsCount +
                ", courseStatus=" + courseStatus +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Course course = (Course) o;

        if (maxStudentsCount != course.maxStudentsCount) return false;
        if (!id.equals(course.id)) return false;
        if (courseType != course.courseType) return false;
        if (!beginDate.equals(course.beginDate)) return false;
        if (!endDate.equals(course.endDate)) return false;
        return courseStatus == course.courseStatus;
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + courseType.hashCode();
        result = 31 * result + beginDate.hashCode();
        result = 31 * result + endDate.hashCode();
        result = 31 * result + maxStudentsCount;
        result = 31 * result + courseStatus.hashCode();
        return result;
    }
}

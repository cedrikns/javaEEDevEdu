package ru.tsedrik.entity;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class Course implements Identifired<UUID>{

    /**
     * Идентификатор курса
     */
    private UUID id;

    /**
     * Тип курса, от которого зависит программа
     */
    private CourseType courseType;

    /**
     * Дата начала курса
     */
    private LocalDate beginDate;

    /**
     * Дата окончания курса
     */
    private LocalDate endDate;

    /**
     * Количество групп на курсе
     */
    private int maxStudentsCount;

    /**
     * Список участников, которые будут обучатья на курсе
     */
    private Set<Student> students;

    /**
     * Статус курса, от которого зависит можно ли на него записаться
     */
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
}

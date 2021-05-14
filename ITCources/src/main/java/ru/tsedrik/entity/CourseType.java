package ru.tsedrik.entity;

public enum CourseType {
    JEE("Java EE"),
    SPRING("Spring Framework"),
    LT("Load Testing"),
    CI("Continuous Integration");

    private String description;

    CourseType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}

package ru.tsedrik.lesson12.hometask1;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public record Author (int id, String firstName, String lastName, LocalDate birthday, List<Integer>books) {
    public Author{
        if ((birthday != null) && (birthday.isAfter(LocalDate.now()))){
            throw new IllegalArgumentException("Birthday can't be future date.");
        }
        if (books == null) {
            books = new ArrayList<>();
        }
    }
}

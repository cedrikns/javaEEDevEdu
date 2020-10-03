package ru.tsedrik.lesson12.hometask1;

import java.time.LocalDate;
import java.util.Set;
import java.util.TreeSet;

public class TestRecordAndTalkativeNPE {
    public static void main(String[] args) {
        Author author = new Author(1, "First", "Author", LocalDate.of(2020, 10, 02), null);
        System.out.println(author);

        //incorrect birthday
//        Author authorEx = new Author(2, "Second", "Author", LocalDate.of(2020, 10, 03), null);

        Book bookWithoutPublisher = new Book(111, new Author(3, "Unknown", "Author", null, null), "bookWithoutPublisher", null, 2001);
        System.out.println(bookWithoutPublisher.publishingHouse());

        Book book1 = new Book(222, new Author(2, "Bryus", "Ekkel", null, null), "Filosofia Java", "the best publishing house", 2020);
        System.out.println(book1);
        System.out.println(book1.publishingHouse());

        //Talkative NPE
//        System.out.println(book1.author().birthday().getYear());

        //Talkative NPE 2
        Book book2 = new Book(333, null, "book2", null, 2020);
        System.out.println(book2.author().books().get(222));

        // Check sorting of books
        Set<Book> books = new TreeSet<>();
        books.add(book1);
        books.add(bookWithoutPublisher);
        System.out.println(books);

    }
}

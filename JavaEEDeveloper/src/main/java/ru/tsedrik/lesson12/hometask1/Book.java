package ru.tsedrik.lesson12.hometask1;

public record Book(int id, Author author, String name, String publishingHouse, int issueYear) implements Comparable<Book>{
    public String publishingHouse() {
        if (publishingHouse == null){
            return  "Unknown publishing house";
        }
        return publishingHouse;
    }

    @Override
    public int compareTo(Book o) {
        return name.toLowerCase().compareTo(o.name.toLowerCase());
    }
}

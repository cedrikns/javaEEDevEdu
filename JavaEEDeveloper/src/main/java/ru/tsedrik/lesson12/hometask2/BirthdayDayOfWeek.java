package ru.tsedrik.lesson12.hometask2;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class BirthdayDayOfWeek {
    public static void main(String[] args) {
        DateTimeFormatter formatter= DateTimeFormatter.ofPattern("dd-MM-yyyy");

        try{
            LocalDate birthday = LocalDate.parse(args[0], formatter);
            System.out.println(birthday);

            String weekDay = switch (birthday.getDayOfWeek()){
                case MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY -> "будний";
                case SATURDAY, SUNDAY -> "выходной";
            };
            System.out.println("Человек родился в " + weekDay + " день.");

        } catch (DateTimeParseException e){
            System.out.println("Date in incorrect format");
            e.printStackTrace();
        }

    }
}

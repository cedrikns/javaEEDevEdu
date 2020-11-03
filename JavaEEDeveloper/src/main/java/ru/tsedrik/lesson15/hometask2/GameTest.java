package ru.tsedrik.lesson15.hometask2;

public class GameTest {
    public static void main(String[] args) {
        Game game1 = Game.getInstance();
        System.out.println(game1);
        Game game2 = Game.getInstance();
        System.out.println(game2);
        System.out.println(game1 == game2);
    }
}

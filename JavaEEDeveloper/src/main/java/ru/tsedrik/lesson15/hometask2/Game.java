package ru.tsedrik.lesson15.hometask2;

public class Game {
    private static Game gameInstance;

    private Game(){
        System.out.println("This is my first and last call");
    }

    public static Game getInstance(){
        if (gameInstance == null){
            synchronized (Game.class){
                if (gameInstance == null){
                    gameInstance = new Game();
                }
            }
        }
        return gameInstance;
    }
}

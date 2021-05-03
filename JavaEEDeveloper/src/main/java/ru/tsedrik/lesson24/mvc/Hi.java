package ru.tsedrik.lesson24.mvc;

public class Hi {

    public static void main(String[] args){
        ViewHi viewHi = new ViewHi();
        ModelHi modelHi = new ModelHi();

        ControllerHi controllerHi = new ControllerHi(viewHi, modelHi);
        controllerHi.start();
    }
}

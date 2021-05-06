package ru.tsedrik.lesson24.mvc;

public class ModelHi {
    private String text;

    public ModelHi() {
    }

    public ModelHi(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}

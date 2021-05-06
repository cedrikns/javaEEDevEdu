package ru.tsedrik.lesson24.mvc;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class ControllerHi implements MouseListener {
    private ViewHi viewHi;
    private ModelHi modelHi;

    public ControllerHi(ViewHi viewHi, ModelHi modelHi) {
        this.viewHi = viewHi;
        this.modelHi = modelHi;
    }

    public void setHelloText(String text){
        modelHi.setText(text);
    }

    public String getHelloText(){
        return modelHi.getText();
    }

    public void start(){
        viewHi.setVisible(true);
        viewHi.setSayHiBtnListener(this);
        viewHi.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void mouseClicked(MouseEvent e) {
        modelHi.setText(viewHi.userInputTextField.getText());
        viewHi.updateHelloLabel(modelHi.getText());
    }

    public void mouseEntered(MouseEvent e){
    }

    public void mouseExited(MouseEvent e){
    }

    public void mousePressed(MouseEvent e){
    }

    public void mouseReleased(MouseEvent e){
    }
}

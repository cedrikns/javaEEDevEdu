package ru.tsedrik.lesson24.mvc;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseListener;

public class ViewHi extends JFrame  {
    // components
    protected JLabel helloLabel = new JLabel("Hello");
    protected JTextField userInputTextField = new JTextField(20);
    private JButton    sayHiBtn    = new JButton("Say Hi");

    /** Constructor */
    ViewHi() {
        //... Layout the components.
        JPanel content = new JPanel();
        content.setLayout(new FlowLayout());
        content.add(new JLabel("Enter your name"));
        content.add(userInputTextField);
        content.add(sayHiBtn);
        content.add(helloLabel);

        //... finalize layout
        this.setContentPane(content);
        this.pack();
        this.setTitle("Simple App - Not MVC");

    }

    public void setSayHiBtnListener(MouseListener mouseListener){
        sayHiBtn.addMouseListener(mouseListener);
    }

    public void updateHelloLabel(String text){
        helloLabel.setText("Hello " + text);
    }

}

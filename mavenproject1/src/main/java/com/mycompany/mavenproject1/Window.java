package com.mycompany.mavenproject1;

import javax.swing.*;

public abstract class Window extends JPanel {

    protected abstract void createButtons();

    protected void showWindow(boolean b, JFrame window) {
        window.setVisible(b);
    }


}


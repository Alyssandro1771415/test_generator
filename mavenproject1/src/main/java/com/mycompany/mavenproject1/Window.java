package com.mycompany.mavenproject1;

import javax.swing.*;

// Classe abstrata window
public abstract class Window extends JPanel {

    // Método para criar os botões de uma janela
    protected abstract void createButtons();

    // Indica se a janela deve ou não ser visível
    protected void showWindow(boolean b, JFrame window) {
        window.setVisible(b);
    }


}


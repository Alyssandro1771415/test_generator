package com.mycompany.mavenproject1;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

public class ConcludedPoPWindow extends JPanel {
    
    JFrame frame;

    public ConcludedPoPWindow() {

        frame = new JFrame();
        frame.setLayout(null);
        frame.setSize(350, 105);
        frame.setUndecorated(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        createButtons();
        createLabels();


    }

    private void createLabels() {
        JLabel labelConcluded = new JLabel ("PROCESSO CONCLUÍDO");
        labelConcluded.setBounds (95, 20, 165, 30);
        frame.add (labelConcluded);
    }

    private void createButtons() {
        JButton buttonOpenMainWindow = new JButton ("VOLTAR AO MENU PRINCIPAL");
        buttonOpenMainWindow.setBounds (70, 50, 210, 30);
        frame.add (buttonOpenMainWindow);

        buttonOpenMainWindow.addActionListener(e -> {
            frame.dispose();
            frame = null;
            MainWindow mainWindow = new MainWindow();
        });

    }


}


package com.mycompany.mavenproject1;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

// Essa classe no geral é um PopUp que é apresentado quando os arquivos são gerados e baixados na máquina do usuário
public class ConcludedPoPWindow extends JPanel {
    
    JFrame frame;

    // Método contrutor da classe
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

    // Criação das labels da interface gráfica
    private void createLabels() {
        JLabel labelConcluded = new JLabel ("PROCESSO CONCLUÍDO");
        labelConcluded.setBounds (95, 20, 165, 30);
        frame.add (labelConcluded);
    }

    // Criação dos botões da interface gráfica
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


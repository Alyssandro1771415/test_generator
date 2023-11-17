package com.mycompany.mavenproject1.View;

import javax.swing.*;


public class ConcludedPoPWindow extends JPanel implements Window {

    // JFrame to display the pop-up window
    JFrame frame;

    // Constructor
    public ConcludedPoPWindow() {
        // Initialize JFrame and configure its properties
        frame = new JFrame();
        frame.setLayout(null);
        frame.setSize(350, 105);
        frame.setUndecorated(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        // Create buttons and labels within the pop-up window
        createButtons();
        createLabels();
    }

    // Implementation of the createLabels method from the Window interface
    @Override
    public void createLabels() {
        // Create and configure a JLabel indicating the completion of a process
        JLabel labelConcluded = new JLabel("PROCESSO CONCLUÍDO");
        labelConcluded.setBounds(95, 20, 165, 30);

        // Add the label to the JFrame
        frame.add(labelConcluded);
    }

    // Implementation of the createButtons method from the Window interface
    @Override
    public void createButtons() {
        // Create and configure a JButton to return to the main menu
        JButton buttonOpenMainWindow = new JButton("VOLTAR AO MENU PRINCIPAL");
        buttonOpenMainWindow.setBounds(70, 50, 210, 30);

        // Add an ActionListener to the button to handle the action when clicked
        buttonOpenMainWindow.addActionListener(e -> {
            // Dispose of the current frame, create a new MainWindow, and set the frame to null
            frame.dispose();
            frame = null;
            MainWindow mainWindow = new MainWindow();
        });

        // Add the button to the JFrame
        frame.add(buttonOpenMainWindow);
    }
}
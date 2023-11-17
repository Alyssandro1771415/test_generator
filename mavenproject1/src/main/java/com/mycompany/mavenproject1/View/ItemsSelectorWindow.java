package com.mycompany.mavenproject1.View;

import javax.swing.*;

import com.mycompany.mavenproject1.Model.Question;

public class ItemsSelectorWindow extends JPanel implements Window {
    private JFrame itemsSelectorWindow;
    private JComboBox itemsQuantitySelector;
    private JCheckBox CheckBoxInfo;
    private int itemsQuantity;
    private int type;
    private Question question;

    // Constructor
    public ItemsSelectorWindow(int type, Question question) {
        // Initialize instance variables with constructor parameters
        this.type = type;
        this.question = question;

        // Initialize JFrame and configure its properties
        itemsSelectorWindow = new JFrame();
        itemsSelectorWindow.setLayout(null);
        itemsSelectorWindow.setSize(452, 194);
        itemsSelectorWindow.setUndecorated(true);
        itemsSelectorWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        itemsSelectorWindow.setResizable(false);
        itemsSelectorWindow.setLocationRelativeTo(null);
        itemsSelectorWindow.setVisible(true);

        // Create labels, buttons, selectors, and checkboxes
        createLabels();
        createButtons();
        createSelectors();

        // Create checkboxes only if the question type is not AFIRMATIVE
        if (!(type == AddQuestionTextWindow.AFIRMATIVE)) {
            createCheboxes();
        }
    }

    // Create and configure the item quantity selector
    private void createSelectors() {
        String[] itemsQuantitySelectorItems = {"3", "4", "5", "6"};
        itemsQuantitySelector = new JComboBox(itemsQuantitySelectorItems);
        itemsQuantitySelector.setBounds(180, 70, 100, 25);
        itemsSelectorWindow.add(itemsQuantitySelector);
    }

    // Create and configure the checkbox for adding "N.D.A" (None of the Above)
    private void createCheboxes() {
        CheckBoxInfo = new JCheckBox("ADICIONAR N.D.A (NENHUMA DAS ALTERNATIVAS)");
        CheckBoxInfo.setBounds(25, 110, 335, 30);
        itemsSelectorWindow.add(CheckBoxInfo);
    }

    // Method to proceed to the next window based on user selection
    private void nextWindow() {
        // Retrieve selected item quantity from the selector
        Object selectedItem = itemsQuantitySelector.getSelectedItem();
        itemsQuantity = Integer.parseInt(selectedItem.toString());

        // Proceed to the next window only if the selected quantity is not 6 and the checkbox is not selected
        if (!(itemsQuantity == 6 && isCheckBoxSelected())) {
            itemsSelectorWindow.dispose();
            itemsSelectorWindow = null;
            ItemsWriterWindow itemsWriterWindow = new ItemsWriterWindow(type, question, isCheckBoxSelected(), itemsQuantity);
        }
    }

    // Method to check if the checkbox is selected
    public boolean isCheckBoxSelected() {
        if (CheckBoxInfo != null) {
            return CheckBoxInfo.isSelected();
        }
        return false; // Return false by default if CheckBoxInfo is null.
    }

    // Implementation of the createButtons method from the Window interface
    @Override
    public void createButtons() {
        JButton buttonNext = new JButton("PRÓXIMO");
        JButton buttonCancel = new JButton("CANCELAR");

        // Add buttons to the JFrame
        itemsSelectorWindow.add(buttonNext);
        itemsSelectorWindow.add(buttonCancel);

        // Set bounds for the buttons
        buttonNext.setBounds(345, 155, 100, 30);
        buttonCancel.setBounds(10, 155, 100, 30);

        // Add ActionListener to the "Next" button to handle the action when clicked
        buttonNext.addActionListener(e -> {
            nextWindow();
        });

        // Add ActionListener to the "Cancel" button to handle the action when clicked
        buttonCancel.addActionListener(e -> {
            itemsSelectorWindow.dispose();
            itemsSelectorWindow = null;
            AddQuestionWindow addQuestionWindow = new AddQuestionWindow();
        });
    }

    // Implementation of the createLabels method from the Window interface
    @Override
    public void createLabels() {
        // Create and configure JLabels to provide information in the window
        JLabel labelAddingQuestion = new JLabel("ADICIONANDO QUESTÃO");
        JLabel labelItemsQuantity = new JLabel("Nº DE ALTERNATIVAS:");
        JLabel labelWarning = new JLabel("Nº DE ALTERNATIVAS LIMITADO A 6, N.D.A ENTRA NESTE TOTAL");

        // Set bounds for JLabels
        labelWarning.setBounds(30, 5, 380, 30);
        labelAddingQuestion.setBounds(30, 25, 165, 30);
        labelItemsQuantity.setBounds(30, 65, 140, 30);

        // Add JLabels to the JFrame
        itemsSelectorWindow.add(labelWarning);
        itemsSelectorWindow.add(labelAddingQuestion);
        itemsSelectorWindow.add(labelItemsQuantity);
    }
}
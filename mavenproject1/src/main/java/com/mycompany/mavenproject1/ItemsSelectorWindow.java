package com.mycompany.mavenproject1;

import java.awt.*;
import javax.swing.*;

public class ItemsSelectorWindow extends Window {
    private JFrame itemsSelectorWindow;
    private JComboBox itemsQuantitySelector;
    private JCheckBox CheckBoxInfo;
    private int itemsQuantity;
    private int type;
    private Question question;

    public ItemsSelectorWindow(int type, Question question) {
        this.type = type;
        this.question = question;

        itemsSelectorWindow = new JFrame();
        itemsSelectorWindow.setLayout(null);
        itemsSelectorWindow.setSize(452, 194);
        itemsSelectorWindow.setUndecorated(true);
        itemsSelectorWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        itemsSelectorWindow.setResizable(false);
        itemsSelectorWindow.setLocationRelativeTo(null);
        itemsSelectorWindow.setVisible(true);

        createLabels();
        createButtons();
        createSelectors();

        if(!(type == AddQuestionTextWindow.AFIRMATIVE)){
            creacteCheboxes();
        }
    }

    private  void createSelectors(){
        String[] itemsQuantitySelectorItems = {"3", "4", "5", "6"};
        itemsQuantitySelector = new JComboBox (itemsQuantitySelectorItems);
        itemsQuantitySelector.setBounds (180, 70, 100, 25);
        itemsSelectorWindow.add (itemsQuantitySelector);
    }

    private void creacteCheboxes(){
        CheckBoxInfo = new JCheckBox ("ADICIONAR N.D.A (NENHUMA DAS ALTERNATIVAS)");
        CheckBoxInfo.setBounds (25, 110, 335, 30);
        itemsSelectorWindow.add (CheckBoxInfo);
    }

    private void nextWindow(){
        Object selectedItem = itemsQuantitySelector.getSelectedItem();
        itemsQuantity = Integer.parseInt(selectedItem.toString());
        if(!(itemsQuantity==6 && isCheckBoxSelected())){
            itemsSelectorWindow.dispose();
            itemsSelectorWindow = null;
            ItemsWriterWindow itemsWriterWindow = new ItemsWriterWindow(type , question, isCheckBoxSelected(), itemsQuantity);
        }
    }

    // Método para retornar se o CheckBox está selecionado
    public boolean isCheckBoxSelected() {
        if (CheckBoxInfo != null) {
            return CheckBoxInfo.isSelected();
        }
        return false; // Retorna false por padrão se CheckBoxInfo for nulo.
    }

    @Override
    protected void createButtons() {
        JButton buttonNext = new JButton("PRÓXIMO");
        JButton buttonCancel = new JButton("CANCELAR");

        itemsSelectorWindow.add (buttonNext);
        itemsSelectorWindow.add (buttonCancel);

        buttonNext.setBounds (345, 155, 100, 30);
        buttonCancel.setBounds (10, 155, 100, 30);

        buttonNext.addActionListener(e -> {
            nextWindow();
        });

        buttonCancel.addActionListener(e -> {
            itemsSelectorWindow.dispose();
            itemsSelectorWindow = null;
            AddQuestionWindow addQuestionWindow = new AddQuestionWindow();
        });

    }

    protected void createLabels() {
        JLabel labelAddingQuestion = new JLabel ("ADICIONANDO QUESTÃO");
        JLabel labelItemsQuantity = new JLabel ("Nº DE ALTERNATIVAS:");
        JLabel labelWarning = new JLabel ("Nº DE ALTERNATIVAS LIMITADO A 6, N.D.A ENTRA NESTE TOTAL");

        labelWarning.setBounds (30, 5, 380, 30);
        labelAddingQuestion.setBounds (30, 25, 165, 30);
        labelItemsQuantity.setBounds (30, 65, 140, 30);

        itemsSelectorWindow.add (labelWarning);
        itemsSelectorWindow.add (labelAddingQuestion);
        itemsSelectorWindow.add (labelItemsQuantity);


    }
}


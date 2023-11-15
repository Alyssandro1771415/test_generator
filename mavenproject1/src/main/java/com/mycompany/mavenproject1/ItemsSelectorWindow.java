package com.mycompany.mavenproject1;

import java.awt.*;
import javax.swing.*;

// Classe para a janela de criação dos itens de questões fechadas e de afirmativas V/F, ela extende a classe window
public class ItemsSelectorWindow extends Window {

    private JFrame itemsSelectorWindow;
    private JComboBox itemsQuantitySelector;
    private JCheckBox CheckBoxInfo;
    private int itemsQuantity;
    private int type;
    private Question question;

    // Construtor da classe
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

        // Dependendo do tipo da questão, as checkboxes serão ou não criadas
        if(!(type == AddQuestionTextWindow.AFIRMATIVE)){
            createCheboxes();
        }
    }

    // Crria os seletores da quantidade de itens da questão
    private  void createSelectors(){
        String[] itemsQuantitySelectorItems = {"3", "4", "5", "6"};
        itemsQuantitySelector = new JComboBox (itemsQuantitySelectorItems);
        itemsQuantitySelector.setBounds (180, 70, 100, 25);
        itemsSelectorWindow.add (itemsQuantitySelector);
    }

    // Criação das checkboxes para caso a questão tenha itens ou seja de afirmativas V/F
    private void createCheboxes(){
        CheckBoxInfo = new JCheckBox ("ADICIONAR N.D.A (NENHUMA DAS ALTERNATIVAS)");
        CheckBoxInfo.setBounds (25, 110, 335, 30);
        itemsSelectorWindow.add (CheckBoxInfo);
    }

    // 
    private void nextWindow(){
        Object selectedItem = itemsQuantitySelector.getSelectedItem();
        itemsQuantity = Integer.parseInt(selectedItem.toString());
        
        // Verifica se a quantidade de itens não é 6 e se a checkbox de adição de opção N.D.A, dai então ele chamará a tela de escrita dos itens
        if(!(itemsQuantity==6 && isCheckBoxSelected())){
            itemsSelectorWindow.dispose();
            itemsSelectorWindow = null;
            ItemsWriterWindow itemsWriterWindow = new ItemsWriterWindow(type , question, isCheckBoxSelected(), itemsQuantity);
        }
    }

    // Método para retornar se o CheckBox de adição de N.D.A está selecionado
    public boolean isCheckBoxSelected() {
        if (CheckBoxInfo != null) {
            return CheckBoxInfo.isSelected();
        }
        return false; // Retorna false por padrão se CheckBoxInfo for nulo.
    }

    // Criação dos botões
    @Override
    protected void createButtons() {
        JButton buttonNext = new JButton("PRÓXIMO");
        JButton buttonCancel = new JButton("CANCELAR");

        itemsSelectorWindow.add (buttonNext);
        itemsSelectorWindow.add (buttonCancel);

        buttonNext.setBounds (345, 155, 100, 30);
        buttonCancel.setBounds (10, 155, 100, 30);

        // Evento disparado caso o botão de PRÓXIMO seja acionado, ele chama a tela seguinte do processo
        buttonNext.addActionListener(e -> {
            nextWindow();
        });

        // Evento disparado caso o botão de CANCELAR seja acionado, ele cancela o processo de criação da questão e volta para a tela anterior
        buttonCancel.addActionListener(e -> {
            itemsSelectorWindow.dispose();
            itemsSelectorWindow = null;
            AddQuestionWindow addQuestionWindow = new AddQuestionWindow();
        });

    }

    // Criação das labels
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


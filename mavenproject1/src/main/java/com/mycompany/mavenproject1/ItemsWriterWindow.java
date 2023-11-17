package com.mycompany.mavenproject1;

import javax.swing.*;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import java.awt.*;

public class ItemsWriterWindow extends JPanel implements Window {
    private JTextArea itemTextArea;
    private JFrame itemsWriteWindow;
    private JLabel labelAddingItem;
    private int itemIndex = 0;
    private Question question;
    private int type;
    private boolean checkBoxValue;
    private int itemsQuantity;

    //CONSTRUCTOR
    public ItemsWriterWindow(int type, Question question, boolean checkBoxValue, int itemsQuantity) {

        // Initialize instance variables with constructor parameters
        this.question = question;
        this.type = type;
        this.checkBoxValue = checkBoxValue;
        this.itemsQuantity = itemsQuantity;

        // Initialize JFrame and configure its properties
        itemsWriteWindow = new JFrame();
        itemsWriteWindow.setLayout(null);
        itemsWriteWindow.setSize(624, 223);
        itemsWriteWindow.setUndecorated(true);
        itemsWriteWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        itemsWriteWindow.setResizable(false);
        itemsWriteWindow.setLocationRelativeTo(null);
        itemsWriteWindow.setVisible(true);

        // Create labels, buttons, and text area
        createLabels();
        createButtons();
        createTextArea();
    }

    // Implementation of the createLabels method from the Window interface
    @Override
    public void createLabels() {
        labelAddingItem = new JLabel("ADICIONANDO ALTERNATIVA " + (1 + itemIndex));
        JLabel labelItemText = new JLabel("TEXTO DA ALTERNATIVA:");
        itemsWriteWindow.add(labelAddingItem);
        itemsWriteWindow.add(labelItemText);
        labelAddingItem.setBounds(220, 0, 180, 35);
        labelItemText.setBounds(10, 35, 150, 25);
    }
    // Create and configure the text area for entering item text
    private void createTextArea() {
        itemTextArea = new JTextArea(5, 5);

        // Configurar DocumentFilter para limitar a quantidade de caracteres
        ((AbstractDocument) itemTextArea.getDocument()).setDocumentFilter(new DocumentFilter() {
            @Override
            public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr)
                    throws BadLocationException {
                if ((fb.getDocument().getLength() + string.length()) <= 200) {
                    super.insertString(fb, offset, string, attr);
                } else {
                    // Excede o limite de 200 caracteres
                    Toolkit.getDefaultToolkit().beep();
                }
            }

            @Override
            public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
                    throws BadLocationException {
                if ((fb.getDocument().getLength() + text.length() - length) <= 200) {
                    super.replace(fb, offset, length, text, attrs);
                } else {
                    // Excede o limite de 200 caracteres
                    Toolkit.getDefaultToolkit().beep();
                }
            }
        });

        // Configurar quebra de linha automática
        itemTextArea.setLineWrap(true);
        itemTextArea.setWrapStyleWord(true);

        itemsWriteWindow.add(itemTextArea);
        itemTextArea.setBounds(10, 65, 605, 90);
    }
    // Implementation of the createButtons method from the Window interface
    @Override
    public void createButtons() {
        JButton buttonNextItem = new JButton("PRÓXIMA ALTERNATIVA");
        JButton buttonCancel = new JButton("CANCELAR");
        itemsWriteWindow.add(buttonNextItem);
        itemsWriteWindow.add(buttonCancel);
        buttonNextItem.setBounds(440, 185, 175, 30);
        buttonCancel.setBounds(10, 185, 100, 30);

        // Add ActionListener to the "Next Item" button to handle the action when clicked
        buttonNextItem.addActionListener(e -> {
            if (!(itemTextArea.getText().isEmpty())) {
                if (itemsQuantity == 0) {
                    // If no more items are required, proceed to QuestionViewWindow
                    itemsWriteWindow.dispose();
                    itemsWriteWindow = null;
                    QuestionViewWindow questionViewWindow = new QuestionViewWindow(question);
                } else {
                    // Set the specific item text based on the question type
                    if (type == AddQuestionTextWindow.CLOSED) {

                        question.setEspecificItem(itemTextArea.getText());
                    } else if (type == AddQuestionTextWindow.AFIRMATIVE) {
                        question.setEspecificItem( itemTextArea.getText() + " ( )");
                    }
                    itemTextArea.setText(""); // Clear the JTextArea
                    itemIndex++;
                    labelAddingItem.setText("ADICIONANDO ALTERNATIVA " + (1 + itemIndex));
                    itemsQuantity--;

                    if (itemsQuantity == 0) {
                        // If no more items are required, set N.D.A (None of the Above) if checkbox is selected
                        if (checkBoxValue == true) {
                            question.setEspecificItem("N.D.A");
                        }
                        // Proceed to QuestionViewWindow
                        QuestionViewWindow questionViewWindow = new QuestionViewWindow(question);
                        itemsWriteWindow.dispose();
                        itemsWriteWindow = null;
                    }
                }
            }
        });

        // Add ActionListener to the "Cancel" button to handle the action when clicked
        buttonCancel.addActionListener(e -> {
            itemsWriteWindow.dispose();
            itemsWriteWindow = null;
            AddQuestionWindow addQuestionWindow = new AddQuestionWindow();
        });
    }
}
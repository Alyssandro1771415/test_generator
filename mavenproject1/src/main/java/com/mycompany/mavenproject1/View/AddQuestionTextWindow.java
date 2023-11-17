package com.mycompany.mavenproject1.View;

import java.awt.*;
import javax.swing.*;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

import com.mycompany.mavenproject1.Model.Question;

public class AddQuestionTextWindow extends JPanel implements Window {

    private JFrame addQuestionTextWindow;
    int type;
    public static final int OPENED = 1;
    public static final int CLOSED = 2;
    public static final int AFIRMATIVE = 3;
    private Question question;
    private JTextArea textAreaQuestionDescription;

    public AddQuestionTextWindow(int type, Question question) {

        this.type = type;
        this.question = question;
        addQuestionTextWindow = new JFrame();
        addQuestionTextWindow.setLayout(null);
        addQuestionTextWindow.setSize(599, 285);
        addQuestionTextWindow.setUndecorated(true);
        addQuestionTextWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        addQuestionTextWindow.setResizable(false);
        addQuestionTextWindow.setLocationRelativeTo(null);
        addQuestionTextWindow.setVisible(true);

        createLabels();
        createTextArea();
        createButtons();

    }


    //BUTTON GENERATER AND CONFIGURATOR METHOD
    @Override
    public void createButtons() {
        JButton butttonCancel = new JButton("CANCELAR");
        JButton buttonNext = new JButton("PRÓXIMO");

        //ADDING TO THE FRAME
        addQuestionTextWindow.add(butttonCancel);
        addQuestionTextWindow.add(buttonNext);

        //SETTING SIZE AND COORDINATES
        butttonCancel.setBounds(15, 245, 100, 30);
        buttonNext.setBounds(490, 245, 100, 30);

        //BUTTON ACTION WHEN CLICKED
        butttonCancel.addActionListener(e -> {
            showWindow(false, addQuestionTextWindow);
            addQuestionTextWindow.dispose();
            addQuestionTextWindow = null;
            AddQuestionWindow addQuestionWindow = new AddQuestionWindow();

        });

        buttonNext.addActionListener(e -> {
            if (!(textAreaQuestionDescription.getText().isEmpty())) {
                setQuestionEntries();
            }

        });
    }

    //LABELS GENERATER AND CONFIGURATOR METHOD
    @Override
    public void createLabels() {
        JLabel labelAddingQuestion = new JLabel("ADICIONANDO QUESTÃO");
        JLabel labelQuestionText = new JLabel("TEXTO DA QUESTÃO:");

        addQuestionTextWindow.add(labelAddingQuestion);
        addQuestionTextWindow.add(labelQuestionText);

        labelAddingQuestion.setBounds(106, 25, 155, 30);
        labelQuestionText.setBounds(40, 65, 135, 25);
    }

    //TEXT AREA GENERATER AND CONFIGURATOR METHOD
    protected void createTextArea() {
        textAreaQuestionDescription = new JTextArea(5, 5);
        textAreaQuestionDescription.setLineWrap(true);
        textAreaQuestionDescription.setWrapStyleWord(true);

        // Crie um DocumentFilter para limitar a quantidade de caracteres
        ((AbstractDocument) textAreaQuestionDescription.getDocument()).setDocumentFilter(new DocumentFilter() {
            @Override
            public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
                    throws BadLocationException {
                int newLength = fb.getDocument().getLength() - length + text.length();
                if (newLength <= 500) {
                    super.replace(fb, offset, length, text, attrs);
                } else {
                    // Você pode mostrar uma mensagem de erro aqui, se desejar
                }
            }
        });

        addQuestionTextWindow.add(textAreaQuestionDescription);

        textAreaQuestionDescription.setBounds(40, 100, 530, 120);
    }

    //SPECIFIC METHOD FOR SETTING VALUES IN TEXTAREAS
    private void setQuestionEntries() {
        question.setQuestion(textAreaQuestionDescription.getText());
        callNextWindow(type);

    }

    //SPECIFIC METHOD TO CALL THE NEXT WINDOW ACCORDING TO THE TYPE OF THE QUESTION
    private void callNextWindow(int type) {

        if (type == OPENED) {

            QuestionViewWindow questionViewWindow = new QuestionViewWindow(question);

            addQuestionTextWindow.dispose();
            addQuestionTextWindow = null;

        } else if (type == CLOSED) {
            ItemsSelectorWindow itemsSelectorWindow = new ItemsSelectorWindow(CLOSED, question);
            addQuestionTextWindow.dispose();
            addQuestionTextWindow = null;
        } else if (type == AFIRMATIVE) {
            ItemsSelectorWindow itemsSelectorWindow = new ItemsSelectorWindow(AFIRMATIVE, question);
            addQuestionTextWindow.dispose();
            addQuestionTextWindow = null;
        }

    }
}

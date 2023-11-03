package com.mycompany.mavenproject1;

import java.awt.*;
import javax.swing.*;

public class QuestionViewWindow extends Window {

    private JButton buttonClose;
    private JFrame questionViewWindow;
    private JFrame lastFrame;
    private Question question;

    public QuestionViewWindow(Question question) {

        this.question = question;
        questionViewWindow = new JFrame();
        questionViewWindow.setLayout(null);
        setWindowSize(question);
        questionViewWindow.setUndecorated(true);
        questionViewWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        questionViewWindow.setResizable(false);
        questionViewWindow.setLocationRelativeTo(null);

        createTextPane(question);
        createTextPane(question);
        createButtons();
        questionViewWindow.setVisible(true);
    }

    public QuestionViewWindow(Question question, JFrame lastFrame) {
        this.question = question;
        this.lastFrame = lastFrame;
        lastFrame.setVisible(false);
        questionViewWindow = new JFrame();
        questionViewWindow.setLayout(null);
        setWindowSize(question);
        questionViewWindow.setUndecorated(true);
        questionViewWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        questionViewWindow.setResizable(false);
        questionViewWindow.setLocationRelativeTo(null);

        createTextPane(question);
        createTextPane(question);
        createButtons(lastFrame);
        questionViewWindow.setVisible(true);
    }

    @Override
    protected void createButtons() {
        buttonClose = new JButton("X");
        buttonClose.setBounds(660, 10, 45, 40);
        buttonClose.setFocusable(false);
        questionViewWindow.add(buttonClose);

        buttonClose.addActionListener(e -> {
            questionViewWindow.setVisible(false);
            questionViewWindow.dispose();
            questionViewWindow = null;

            queryExecutions consultant = new queryExecutions();
            consultant.dataUpload(question);

            ConcludedPoPWindow concludedPoPWindow = new ConcludedPoPWindow();
        });
    }

    protected void createButtons(JFrame frame) {
        buttonClose = new JButton("X");
        buttonClose.setBounds(660, 10, 45, 40);
        buttonClose.setFocusable(false);
        questionViewWindow.add(buttonClose);

        buttonClose.addActionListener(e -> {
            questionViewWindow.setVisible(false);
            questionViewWindow.dispose();
            questionViewWindow = null;
            frame.setVisible(true);
        });
    }

    protected void createTextPane(Question question) {
        JTextPane textPaneQuestionDescription = new JTextPane();
        textPaneQuestionDescription.setText(question.getQuestion());
        textPaneQuestionDescription.setEditable(false);
        textPaneQuestionDescription.setBounds(45, 50, 600, getTextPaneSize(question));
        questionViewWindow.add(textPaneQuestionDescription);

        if (question.countItems(question.getItems()) != 0) {

            int itemPos = getTextPaneSize(question) + 70;
            JTextPane[] textPane_Item = new JTextPane[7];
            String[] lettersArray = { "A)", "B)", "C)", "D)", "E)", "F)" };
            for (int i = 0; i < question.countItems(question.getItems()); i++) {
                if (i != 0) {
                    itemPos += 50;
                }
                textPane_Item[i] = new JTextPane();
                textPane_Item[i].setText(lettersArray[i] + " " + question.getSpecificItem(i));
                textPane_Item[i].setEditable(false);
                textPane_Item[i].setBounds(45, itemPos, 600, 40);
                questionViewWindow.add(textPane_Item[i]);
            }
        }

    }

    private void setWindowSize(Question question) {
        int windowHeight = (getTextPaneSize(question) + 200);
        int itemsNumber = question.countItems(question.getItems());

        switch (itemsNumber) {
            case 2:
                windowHeight += 25;
                questionViewWindow.setSize(712, windowHeight);
                break;
            case 3:
                windowHeight += 50;
                questionViewWindow.setSize(712, windowHeight);
                break;
            case 4:
                windowHeight += 100;
                questionViewWindow.setSize(712, windowHeight);
                break;
            case 5:
                windowHeight += 150;
                questionViewWindow.setSize(712, windowHeight);
                break;
            case 6:
                windowHeight += 200;
                questionViewWindow.setSize(712, windowHeight);
                break;
            case 7:
                windowHeight += 250;
                questionViewWindow.setSize(712, windowHeight);
                break;
            case 0:

                questionViewWindow.setSize(712, getTextPaneSize(question) + 100);
                break;
            default:
                break;
        }
    }

    private int getTextPaneSize(Question question) {

        int textLength = question.getQuestion().length();
        int QuestionDescriptionHeigh = 0;
        if (textLength >= 100 && textLength < 200) {
            return 36;

        } else if (textLength >= 200 && textLength < 300) {
            return 62;

        } else if (textLength >= 300 && textLength < 400) {
            return 88;

        } else if (textLength >= 400 && textLength < 1000) {
            return 114;

        } else if (textLength >= 1000) {
            return 140;

        }
        return 22;
    }

}
package com.mycompany.mavenproject1;

import java.awt.*;
import javax.swing.*;

public class QuestionViewWindow extends Window {

    private JButton buttonClose;
    private JFrame questionViewWindow;
    private JFrame lastFrame;
    private Question question;

    // Construtor da classe QuestionViewWindow para exibir uma única pergunta
    public QuestionViewWindow(Question question) {
        // Inicializar a janela de visualização de pergunta
        this.question = question;
        questionViewWindow = new JFrame();
        questionViewWindow.setLayout(null);
        setWindowSize(question);
        questionViewWindow.setUndecorated(true);
        questionViewWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        questionViewWindow.setResizable(false);
        questionViewWindow.setLocationRelativeTo(null);

        // Criar painéis de texto e botões na janela de visualização
        createTextPane(question);
        createButtons();

        // Tornar a janela de visualização visível
        questionViewWindow.setVisible(true);
    }

    // Construtor adicional para a classe QuestionViewWindow, recebendo a janela
    // anterior como parâmetro
    public QuestionViewWindow(Question question, JFrame lastFrame) {
        // Inicializar a janela de visualização de pergunta
        this.question = question;
        this.lastFrame = lastFrame;

        // Ocultar a janela anterior, se fornecida
        if (lastFrame != null) {
            lastFrame.setVisible(false);
        }

        // Inicializar a janela de visualização de pergunta
        questionViewWindow = new JFrame();
        questionViewWindow.setLayout(null);
        setWindowSize(question);
        questionViewWindow.setUndecorated(true);
        questionViewWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        questionViewWindow.setResizable(false);
        questionViewWindow.setLocationRelativeTo(null);

        // Criar painéis de texto e botões na janela de visualização, passando a janela
        // anterior, se fornecida
        createTextPane(question);
        createButtons(lastFrame);

        // Tornar a janela de visualização visível
        questionViewWindow.setVisible(true);
    }

    // Método para criar botões na janela de visualização da pergunta
    @Override
    protected void createButtons() {
        // Criar botão de fechar
        buttonClose = new JButton("X");
        buttonClose.setBounds(660, 10, 45, 40);
        buttonClose.setFocusable(false);
        questionViewWindow.add(buttonClose);

        // Adicionar um ouvinte ao botão de fechar
        buttonClose.addActionListener(e -> {
            // Esconder, fechar e limpar a janela de visualização
            questionViewWindow.setVisible(false);
            questionViewWindow.dispose();
            questionViewWindow = null;

            // Realizar operação de upload de dados e abrir uma nova janela concluída
            QueryExecutions consultant = new QueryExecutions();
            consultant.dataUpload(question);
            ConcludedPoPWindow concludedPoPWindow = new ConcludedPoPWindow();
        });
    }

    // Método adicional para criar botões na janela de visualização da pergunta, recebendo a janela anterior como parâmetro
    protected void createButtons(JFrame frame) {
        // Criar botão de fechar
        buttonClose = new JButton("X");
        buttonClose.setBounds(660, 10, 45, 40);
        buttonClose.setFocusable(false);
        questionViewWindow.add(buttonClose);

        // Adicionar um ouvinte ao botão de fechar
        buttonClose.addActionListener(e -> {
            // Esconder, fechar e limpar a janela de visualização
            questionViewWindow.setVisible(false);
            questionViewWindow.dispose();
            questionViewWindow = null;

            // Tornar visível a janela anterior que foi passada como parâmetro
            frame.setVisible(true);
        });
    }

    // Janela que representa um painel para visualizar uma questão e seus itens
    protected void createTextPane(Question question) {
        JTextPane textPaneQuestionDescription = new JTextPane();
        textPaneQuestionDescription.setText(question.getQuestion());
        textPaneQuestionDescription.setEditable(false);
        textPaneQuestionDescription.setBounds(45, 50, 600, getTextPaneSize(question));
        questionViewWindow.add(textPaneQuestionDescription);

        // Verifica se a quantidade de itens não é 0, ou seja, se ela não é uma questãoa aberta pois caso seja não haverá itens a apresentar
        if (question.countItems(question.getItems()) != 0) {

            int itemPos = getTextPaneSize(question) + 70;
            JTextPane[] textPane_Item = new JTextPane[7];
            String[] lettersArray = { "A)", "B)", "C)", "D)", "E)", "F)" };

            // Varre a lista de itens e cria o JPanel de cada uma delas
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

    // Configura o tamanho da janela de acordo com a quantidade de itens existentes
    private void setWindowSize(Question question) {
        int windowHeight = (getTextPaneSize(question) + 200); // tamanho padrão
        int itemsNumber = question.countItems(question.getItems()); // Quantidade de itens

        switch (itemsNumber) {
            case 2:
                windowHeight += 25; // Soma um determinado valor ao já existente a depender da quantidade de itens 
                questionViewWindow.setSize(712, windowHeight);
                break;
            case 3:
                windowHeight += 50; // Soma um determinado valor ao já existente a depender da quantidade de itens
                questionViewWindow.setSize(712, windowHeight);
                break;
            case 4:
                windowHeight += 100;// Soma um determinado valor ao já existente a depender da quantidade de itens
                questionViewWindow.setSize(712, windowHeight);
                break;
            case 5:
                windowHeight += 150;// Soma um determinado valor ao já existente a depender da quantidade de itens
                questionViewWindow.setSize(712, windowHeight);
                break;
            case 6:
                windowHeight += 200;// Soma um determinado valor ao já existente a depender da quantidade de itens
                questionViewWindow.setSize(712, windowHeight);
                break;
            case 7:
                windowHeight += 250;// Soma um determinado valor ao já existente a depender da quantidade de itens
                questionViewWindow.setSize(712, windowHeight);
                break;
            case 0: // Soma um determinado valor ao já existente a depender da quantidade de itens

                questionViewWindow.setSize(712, getTextPaneSize(question) + 100);
                break;
            default:
                break;
        }
    }

    // Método para capturar o tamanho de um painel
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
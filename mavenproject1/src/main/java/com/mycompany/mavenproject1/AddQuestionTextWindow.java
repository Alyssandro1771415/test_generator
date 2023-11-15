package com.mycompany.mavenproject1;

import java.awt.*;
import javax.swing.*;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

public class AddQuestionTextWindow extends Window {

    // JFrame para a janela de adição de texto da pergunta
    private JFrame addQuestionTextWindow;

    // Tipo de janela (aberta, fechada, afirmativa)
    int type;

    // Constantes para tipos de janela
    public static final int OPENED = 1;
    public static final int CLOSED = 2;
    public static final int AFIRMATIVE = 3;

    // Objeto Question para armazenar informações da pergunta
    private Question question;

    // JTextArea para inserção do texto da pergunta
    private JTextArea textAreaQuestionDescription;

    // Construtor da classe
    public AddQuestionTextWindow(int type, Question question) {

        // Inicializa variáveis
        this.type = type;
        this.question = question;

        // Configuração da janela
        addQuestionTextWindow = new JFrame();
        addQuestionTextWindow.setLayout(null);
        addQuestionTextWindow.setSize(599, 285);
        addQuestionTextWindow.setUndecorated(true);
        addQuestionTextWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        addQuestionTextWindow.setResizable(false);
        addQuestionTextWindow.setLocationRelativeTo(null);
        addQuestionTextWindow.setVisible(true);

        // Criação de labels, área de texto e botões
        createLabels();
        createTextArea();
        createButtons();

    }

    // Método para criar botões
    @Override
    protected void createButtons() {
        // Botões para cancelar e avançar para a próxima etapa
        JButton butttonCancel = new JButton("CANCELAR");
        JButton buttonNext = new JButton("PRÓXIMO");

        // Adiciona os botões à janela
        addQuestionTextWindow.add(butttonCancel);
        addQuestionTextWindow.add(buttonNext);

        // Configura a posição dos botões
        butttonCancel.setBounds(15, 245, 100, 30);
        buttonNext.setBounds(490, 245, 100, 30);

        // Define ação para o botão Cancelar
        butttonCancel.addActionListener(e -> {
            showWindow(false, addQuestionTextWindow);
            addQuestionTextWindow.dispose();
            addQuestionTextWindow = null;
            AddQuestionWindow addQuestionWindow = new AddQuestionWindow();
        });

        // Define ação para o botão Próximo
        buttonNext.addActionListener(e -> {
            if (!(textAreaQuestionDescription.getText().isEmpty())) {
                setQuestionEntries();
            }
        });
    }

    // Método para criar labels
    protected void createLabels() {
        // Labels para indicar a função da janela e a entrada do texto da pergunta
        JLabel labelAddingQuestion = new JLabel("ADICIONANDO QUESTÃO");
        JLabel labelQuestionText = new JLabel("TEXTO DA QUESTÃO:");

        // Adiciona labels à janela
        addQuestionTextWindow.add(labelAddingQuestion);
        addQuestionTextWindow.add(labelQuestionText);

        // Configura a posição das labels
        labelAddingQuestion.setBounds(106, 25, 155, 30);
        labelQuestionText.setBounds(40, 65, 135, 25);
    }

    // Método para criar a área de texto
    protected void createTextArea() {
        // JTextArea para inserção do texto da pergunta, com limite de caracteres
        textAreaQuestionDescription = new JTextArea(5, 5);
        textAreaQuestionDescription.setLineWrap(true);
        textAreaQuestionDescription.setWrapStyleWord(true);

        // Cria um DocumentFilter para limitar a quantidade de caracteres
        ((AbstractDocument) textAreaQuestionDescription.getDocument()).setDocumentFilter(new DocumentFilter() {
            @Override
            public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
                    throws BadLocationException {
                int newLength = fb.getDocument().getLength() - length + text.length();
                if (newLength <= 500) {
                    super.replace(fb, offset, length, text, attrs);
                } else {
                    // Pode mostrar uma mensagem de erro aqui, se desejar
                }
            }
        });

        // Adiciona a área de texto à janela
        addQuestionTextWindow.add(textAreaQuestionDescription);

        // Configura a posição da área de texto
        textAreaQuestionDescription.setBounds(40, 100, 530, 120);
    }

    // Método para configurar as entradas da pergunta
    private void setQuestionEntries() {
        // Define o texto da pergunta no objeto Question
        question.setQuestion(textAreaQuestionDescription.getText());
        // Chama a próxima janela com base no tipo
        callNextWindow(type);
    }

    // Método para chamar a próxima janela com base no tipo
    private void callNextWindow(int type) {
        // Verifica o tipo de janela e chama a próxima janela correspondente
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

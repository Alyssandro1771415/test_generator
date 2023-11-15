package com.mycompany.mavenproject1;

import javax.swing.*;

public class AddQuestionWindow extends Window {

    // Objeto Question para armazenar informações da pergunta
    private Question question;

    // JFrame para a janela de adição de pergunta
    private JFrame frame;

    // Componentes de entrada (campos de texto e botões de opção)
    private JTextField entryContent;
    private JTextField entrySchoolSubject;
    private JRadioButton radioButtonEasy;
    private JRadioButton radioButtonNormal;
    private JRadioButton radioButtonHard;
    private JRadioButton radioButtonOpenedQuestion;
    private JRadioButton radioButtonClosedQuestion;
    private JRadioButton radioButtonAfirmativeQuestion;

    // Tipo de pergunta
    private int type = 0;

    // Construtor da classe
    public AddQuestionWindow() {
        this.question = new Question();
        frame = new JFrame();
        frame.setLayout(null);
        frame.setSize(369, 350);
        frame.setUndecorated(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);

        // Método para criar botões, labels, campos de texto e botões de opção
        createButtons();
        createLabels();
        createRadioButtons();
        createEntrySpace();
        setWindowVisible(true);
    }

    // Método para criar os campos de texto
    private void createEntrySpace() {
        entrySchoolSubject = new JTextField(5);
        entryContent = new JTextField(5);

        frame.add(entrySchoolSubject);
        frame.add(entryContent);

        entrySchoolSubject.setBounds(120, 80, 195, 25);
        entryContent.setBounds(120, 125, 195, 25);
    }

    // Método para criar os botões de opção
    private void createRadioButtons() {
        radioButtonEasy = new JRadioButton("FÁCIL");
        radioButtonNormal = new JRadioButton("MÉDIA");
        radioButtonHard = new JRadioButton("DIFÍCIL");
        radioButtonOpenedQuestion = new JRadioButton("ABERTA");
        radioButtonClosedQuestion = new JRadioButton("FECHADA");
        radioButtonAfirmativeQuestion = new JRadioButton("VERDADEIRO OU FALSO");

        frame.add(radioButtonEasy);
        frame.add(radioButtonNormal);
        frame.add(radioButtonHard);
        frame.add(radioButtonOpenedQuestion);
        frame.add(radioButtonClosedQuestion);
        frame.add(radioButtonAfirmativeQuestion);

        radioButtonEasy.setBounds(35, 205, 85, 25);
        radioButtonNormal.setBounds(120, 205, 80, 25);
        radioButtonHard.setBounds(215, 205, 100, 25);
        radioButtonOpenedQuestion.setBounds(35, 275, 75, 25);
        radioButtonClosedQuestion.setBounds(110, 275, 85, 25);
        radioButtonAfirmativeQuestion.setBounds(195, 275, 170, 25);

        ButtonGroup radioButtonGroupLevel = new ButtonGroup();
        radioButtonGroupLevel.add(radioButtonEasy);
        radioButtonGroupLevel.add(radioButtonNormal);
        radioButtonGroupLevel.add(radioButtonHard);

        ButtonGroup radioButtonGroupType = new ButtonGroup();
        radioButtonGroupType.add(radioButtonOpenedQuestion);
        radioButtonGroupType.add(radioButtonClosedQuestion);
        radioButtonGroupType.add(radioButtonAfirmativeQuestion);
    }

    // Método para criar botões
    @Override
    protected void createButtons() {
        JButton butttonCancel = new JButton("CANCELAR");
        JButton buttonNext = new JButton("PRÓXIMO");

        frame.add(butttonCancel);
        frame.add(buttonNext);

        butttonCancel.setBounds(10, 310, 100, 30);
        buttonNext.setBounds(260, 310, 100, 30);

        butttonCancel.addActionListener(e -> {
            frame.dispose();
            frame = null;
            MainWindow mainWindow = new MainWindow();
        });

        buttonNext.addActionListener(e -> {
            if (radioButtonEasy.isSelected() || radioButtonNormal.isSelected() || radioButtonHard.isSelected()) {
                if (radioButtonOpenedQuestion.isSelected() ||
                    radioButtonClosedQuestion.isSelected() ||
                    radioButtonAfirmativeQuestion.isSelected()){
                    if(!entrySchoolSubject.getText().isEmpty() && !entryContent.getText().isEmpty()){
                        setQuestionEntries();
                    }
                }
            }
        });
    }

    // Método para criar labels
    protected void createLabels(){
        JLabel labelSchoolSubject = new JLabel("DISCIPLINA:");
        JLabel labelContent = new JLabel("SUBÁREA:");
        JLabel labelQuestionLevel = new JLabel("NÍVEL DA QUESTÃO:");
        JLabel labelQuestionType = new JLabel("TIPO DA QUESTÃO:");
        JLabel labelAddingQuestion = new JLabel("ADICIONANDO QUESTÃO");

        frame.add(labelSchoolSubject);
        frame.add(labelContent);
        frame.add(labelQuestionLevel);
        frame.add(labelQuestionType);
        frame.add(labelAddingQuestion);

        labelSchoolSubject.setBounds(35, 80, 80, 30);
        labelContent.setBounds(35, 125, 70, 25);
        labelQuestionLevel.setBounds(35, 170, 125, 25);
        labelQuestionType.setBounds(35, 240, 125, 25);
        labelAddingQuestion.setBounds(105, 25, 155, 30);
    }

    // Método para configurar as entradas da pergunta com base na opção escolhida pelo usuário nos radioButtons
    private void setQuestionEntries(){
        question.setSchoolSubject(entrySchoolSubject.getText().toLowerCase());
        question.setContent(entryContent.getText().toLowerCase());

        if (radioButtonEasy.isSelected()) {
            question.setDifficult(1);
        } else if (radioButtonNormal.isSelected()) {
            question.setDifficult(2);
        } else if (radioButtonHard.isSelected()) {
            question.setDifficult(3);
        }

        if (radioButtonOpenedQuestion.isSelected()) {
            type = 1;
        } else if (radioButtonClosedQuestion.isSelected()) {
            type = 2;
        } else if (radioButtonAfirmativeQuestion.isSelected()) {
            type = 3;
        }

        callNextwindow(type);
    }

    // Método para chamar a próxima janela com base no tipo
    private void callNextwindow(int type){
        AddQuestionTextWindow addQuestionTextWindow = new AddQuestionTextWindow(type, question);
        frame.dispose();
        frame = null;
    }

    // Método para configurar a visibilidade da janela
    protected void setWindowVisible(boolean value){
        frame.setVisible(value);
    }
}

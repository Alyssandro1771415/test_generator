package com.mycompany.mavenproject1;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.border.LineBorder;

public class MainWindow extends JPanel {

    private Test newTest;
    private JFrame mainWindow;
    private JTextField entryTestNumber;
    private JTextField entrySchoolSubject;
    private JTextField entryInstitution;
    private JTextField entryEducatorName;

    public MainWindow() {

        mainWindow = new JFrame();
        mainWindow.setLayout(null);
        mainWindow.setSize(754, 427);
        mainWindow.setUndecorated(true);
        mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainWindow.setResizable(false);
        mainWindow.setLocationRelativeTo(null);

        createButtons();
        createLabels();
        createTexFields();
        createVisualSquares();
        showWindow(true);
    }

    public void createVisualSquares() {
        JPanel rightPanel = new JPanel();
        JPanel leftPanel = new JPanel();
        rightPanel.setBounds(377, 50, 377, 330);
        rightPanel.setBorder(new LineBorder(Color.BLACK));
        mainWindow.add(rightPanel);
        leftPanel.setBounds(0, 50, 754, 330);
        leftPanel.setBorder(new LineBorder(Color.BLACK));
        mainWindow.add(leftPanel);
    }

    public void createTexFields() {

        entryTestNumber = new JTextField(5);
        entrySchoolSubject = new JTextField(5);
        entryInstitution = new JTextField(5);
        entryEducatorName = new JTextField(5);

        mainWindow.add(entryTestNumber);
        mainWindow.add(entrySchoolSubject);
        mainWindow.add(entryInstitution);
        mainWindow.add(entryEducatorName);

        entryTestNumber.setBounds(570, 110, 170, 25);
        entrySchoolSubject.setBounds(495, 230, 245, 25);
        entryInstitution.setBounds(495, 150, 245, 25);
        entryEducatorName.setBounds(495, 190, 245, 25);

    }

    public void createButtons() {

        JButton buttonAddQuestions = new JButton("ADICIONAR QUESTÕES");
        JButton buttonDelQuestions = new JButton("DELETAR/PESQUISAR QUESTÕES");
        JButton buttonClear = new JButton("LIMPAR");
        JButton buttonGenerateRandomTest = new JButton("GERAR PROVA ALEATÓRIA");
        JButton buttonGenerateManualTest = new JButton("GERAR PROVA MANUAL");
        JButton buttonClose = new JButton("FECHAR");

        buttonAddQuestions.setFocusable(false);
        buttonDelQuestions.setFocusable(false);
        buttonClear.setFocusable(false);
        buttonGenerateRandomTest.setFocusable(false);
        buttonGenerateManualTest.setFocusable(false);
        buttonClose.setFocusable(false);

        buttonAddQuestions.setToolTipText("CLIQUE PARA ADICIONAR UMA NOVA QUESTÃO AO BANCO DE QUESTÕES.");
        buttonDelQuestions.setToolTipText("CLIQUE PARA VIZUALIZAR QUESTÕES OU DELETAR QUESTÕES DO BANCO DE QUESTÕES.");
        buttonClear.setToolTipText("CLIQUE PARA LIMPAR TODAS AS CAIXAS DE ENTRADA.");
        buttonGenerateRandomTest.setToolTipText("CLIQUE PARA GERAR UMA AVALIAÇÃO ALEATORIAMENTE.");
        buttonGenerateManualTest.setToolTipText("CLIQUE PARA GERAR MANUALMENTE UMA AVALIAÇÃO.");
        buttonClose.setToolTipText("CLIQUE PARA FECHAR O PROGRAMA");

        mainWindow.add(buttonAddQuestions);
        mainWindow.add(buttonDelQuestions);
        mainWindow.add(buttonClear);
        mainWindow.add(buttonGenerateRandomTest);
        mainWindow.add(buttonGenerateManualTest);
        mainWindow.add(buttonClose);


        buttonAddQuestions.setBounds(70, 110, 240, 30);
        buttonDelQuestions.setBounds(70, 160, 240, 30);
        buttonClear.setBounds(405, 325, 100, 30);
        buttonGenerateRandomTest.setBounds(555, 270, 190, 30);
        buttonGenerateManualTest.setBounds(555, 325, 190, 30);
        buttonClose.setBounds(645, 10, 100, 25);

        buttonClose.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        buttonClear.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                //Limpa o campo de texto das entradas
                entryEducatorName.setText("");
                entryInstitution.setText("");
                entryTestNumber.setText("");
                entrySchoolSubject.setText("");


            }
        });

        buttonGenerateManualTest.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (!entryInstitution.getText().isEmpty() && !entrySchoolSubject.getText().isEmpty() && !entryEducatorName.getText().isEmpty() && !entryTestNumber.getText().isEmpty()) {
                    try {
                        int testsNumber = Integer.parseInt(entryTestNumber.getText());
                        Test newTest = getDataFromEntries();
                        mainWindow.dispose();
                        mainWindow = null;
                        SelectQuestionWindow selectQuestionWindow = new SelectQuestionWindow(newTest);
                    } catch (NumberFormatException ex) {
                        // Tratar o caso em que não é um número inteiro
                        JOptionPane.showMessageDialog(null, "O número de questões deve ser um valor inteiro.");
                    }
                }
            }
        });

        buttonGenerateRandomTest.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (!entryInstitution.getText().isEmpty() && !entrySchoolSubject.getText().isEmpty() && !entryEducatorName.getText().isEmpty() && !entryTestNumber.getText().isEmpty()) {
                    try {
                        int testsNumber = Integer.parseInt(entryTestNumber.getText());
                        Test newTest = getDataFromEntries();
                        mainWindow.dispose();
                        mainWindow = null;
                        AddRandomTestWindow addRandomTestWindow = new AddRandomTestWindow(newTest);
                    } catch (NumberFormatException ex) {
                        // Tratar o caso em que não é um número inteiro
                        JOptionPane.showMessageDialog(null, "O número de questões deve ser um valor inteiro.");
                    }
                }
            }
        });

        buttonAddQuestions.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showWindow(false);
                mainWindow.dispose();
                mainWindow = null;
                AddQuestionWindow addQuestionWindow = new AddQuestionWindow();
            }
        });

        buttonDelQuestions.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showWindow(false);
                mainWindow.dispose();
                mainWindow = null;
                //--------------
                QuestionListWindow questionListWindow = new QuestionListWindow();

            }
        });


    }

    public void createLabels() {
        JLabel labelQuestionRegistration = new JLabel("CADASTRO DE QUESTÕES");
        JLabel labelTestRegistration = new JLabel("CADASTRO DE AVALIAÇÃO");
        JLabel labelEntryTestsNumber = new JLabel("NUMERO DE AVALIAÇÕES");
        JLabel labelEntryInstitution = new JLabel("INSTITUIÇÃO");
        JLabel labelEntryEducadorName = new JLabel("PROFESSOR");
        JLabel labelEntrySchoolSubject = new JLabel("DISCIPLINA");
        JLabel labelTitle_SGAA = new JLabel("SGGA - SISTEMA GERENCIADOR DE AVALIAÇÕES ACADÊMICAS");
        ImageIcon icon = new ImageIcon(loadImage("UEPBLOGO.png"));//COLOCAR CASO DE ERRO PARA QUANDO NÃO CARREGAR A IMAGEM
        JLabel labelImageUEPB = new JLabel(icon);

        mainWindow.add(labelQuestionRegistration);
        mainWindow.add(labelTestRegistration);
        mainWindow.add(labelEntryTestsNumber);
        mainWindow.add(labelEntryInstitution);
        mainWindow.add(labelEntryEducadorName);
        mainWindow.add(labelEntrySchoolSubject);
        mainWindow.add(labelTitle_SGAA);
        mainWindow.add(labelImageUEPB);

        labelQuestionRegistration.setBounds(110, 65, 165, 25);
        labelTestRegistration.setBounds(475, 65, 165, 25);
        labelEntryTestsNumber.setBounds(405, 110, 155, 25);
        labelEntryInstitution.setBounds(405, 150, 90, 25);
        labelEntryEducadorName.setBounds(405, 190, 90, 25);
        labelEntrySchoolSubject.setBounds(405, 230, 100, 25);
        labelTitle_SGAA.setBounds(70, 10, 435, 25);
        labelImageUEPB.setBounds(20, 220, 350, 158);

    }

    public Test getDataFromEntries(){

        ArrayList<Question> questionsList = new ArrayList<>();
        newTest = new Test

                (entryInstitution.getText(),
                entrySchoolSubject.getText(),
                entryEducatorName.getText(),
                Integer.parseInt(entryTestNumber.getText()),
                questionsList);

        return newTest;

    }

    public void showWindow(boolean value){
        mainWindow.setVisible(value);
    }

    private BufferedImage loadImage(String url) {
        BufferedImage img = null;
        InputStream is = getClass().getResourceAsStream(url);
        try {
            img = ImageIO.read(is);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return img;
    }
}
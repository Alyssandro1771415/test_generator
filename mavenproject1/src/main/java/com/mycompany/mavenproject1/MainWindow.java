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

// Classe que extende a classe JPanel, ela representa a tela principal e inicial à qual o usuário terá seu primeiro contato com o programa 
public class MainWindow extends JPanel {

    private Test newTest;
    private JFrame mainWindow;
    private JTextField entryTestNumber;
    private JTextField entrySchoolSubject;
    private JTextField entryInstitution;
    private JTextField entryEducatorName;

    // Método contrutor da classe
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

    // Criação e configuração das duas métades da tela principal
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

    // Criação das TextFields para obter atributos que serão usados na criação dos arquivos das avaliações
    public void createTexFields() {

        entryTestNumber = new JTextField(5);
        entrySchoolSubject = new JTextField(5);
        entryInstitution = new JTextField(5);
        entryEducatorName = new JTextField(5);

        mainWindow.add(entryTestNumber); // Entrada da quantidade de randomizações das avaliações a serem geradas 
        mainWindow.add(entrySchoolSubject); // Entrada do nome do conteúdo a ser usado na avaliação, servirá para quando a tabela de questões aparecer ter apenas questões do conteúdo desejado
        mainWindow.add(entryInstitution); // Nome da instituição de ensino para ser inserido no arquivo avaliação
        mainWindow.add(entryEducatorName); // Nome do educador para ser inserido no arquivo da avaliação

        entryTestNumber.setBounds(570, 110, 170, 25);
        entrySchoolSubject.setBounds(495, 230, 245, 25);
        entryInstitution.setBounds(495, 150, 245, 25);
        entryEducatorName.setBounds(495, 190, 245, 25);

    }

    // Criação dos botões da janela
    public void createButtons() {

        JButton buttonAddQuestions = new JButton("ADICIONAR QUESTÕES"); // Botão para cadastramento de questões na DB
        JButton buttonDelQuestions = new JButton("DELETAR/PESQUISAR QUESTÕES"); // Botão para chamada de uma janela de pesquisa de questões e de exclusão delas
        JButton buttonClear = new JButton("LIMPAR"); // Limpa todos os campos de entrada de texto
        JButton buttonGenerateRandomTest = new JButton("GERAR PROVA ALEATÓRIA"); // Botão para chamada da janela de criação de avaliações aleatórias
        JButton buttonGenerateManualTest = new JButton("GERAR PROVA MANUAL"); // Botão para chamada da janela de criação de avaliações manualmente
        JButton buttonClose = new JButton("FECHAR"); // Botão de encerramento do programa

        buttonAddQuestions.setFocusable(false);
        buttonDelQuestions.setFocusable(false);
        buttonClear.setFocusable(false);
        buttonGenerateRandomTest.setFocusable(false);
        buttonGenerateManualTest.setFocusable(false);
        buttonClose.setFocusable(false);

        // Mensagens para aparecerem quando o mouse estiver sobre os botões
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

        // Adição da ação de fechar o programa ao acionar o botão FECHAR
        buttonClose.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        // Adição da ação de limpeza dos campos de entrada ao acionar o botão LIMPAR
        buttonClear.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                //Limpa o campo de texto das entradas
                entryEducatorName.setText("");
                entryInstitution.setText("");
                entryTestNumber.setText("");
                entrySchoolSubject.setText("");


            }
        });

        // Adição da ação de chamada da janela de geração manual de avaliação 
        buttonGenerateManualTest.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                // Verificando se os campos com os dados que serão usados na geração do arquivo não estão vazios
                if (!entryInstitution.getText().isEmpty() && !entrySchoolSubject.getText().isEmpty() && !entryEducatorName.getText().isEmpty() && !entryTestNumber.getText().isEmpty()) {
                    try {
                        int testsNumber = Integer.parseInt(entryTestNumber.getText()); // Passando o valor do campo corrspondente a quantidade de avaliações randomizadas diferents para uma variável de tipo inteio
                        Test newTest = getDataFromEntries(); // Passa os valores das entradas para uma varável do tipo da classe Test
                        mainWindow.dispose(); // Fechando a janela MainWindow
                        mainWindow = null;

                        // Chamando uma janela da classe SelectQuestionWindow onde o processo de criação da avaliação manualmentee será dado seguimento
                        SelectQuestionWindow selectQuestionWindow = new SelectQuestionWindow(newTest, Integer.parseInt(entryTestNumber.getText()), entrySchoolSubject.getText().toLowerCase());
                    } catch (NumberFormatException ex) {
                        // Tratar o caso em que não é um número inteiro
                        JOptionPane.showMessageDialog(null, "O número de questões deve ser um valor inteiro.");
                    }
                }
            }
        });

        // Adição da ação de chamada da janela de geração aleatória de avaliação 
        buttonGenerateRandomTest.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                
                // Verifica se não há campos não estão vazios
                if (!entryInstitution.getText().isEmpty() && !entrySchoolSubject.getText().isEmpty() && !entryEducatorName.getText().isEmpty() && !entryTestNumber.getText().isEmpty()) {
                    try {
                        int testsNumber = Integer.parseInt(entryTestNumber.getText()); // Passando o valor do campo corrspondente a quantidade de avaliações randomizadas diferents para uma variável de tipo inteio
                        Test newTest = getDataFromEntries(); // Passa os valores das entradas para uma varável do tipo da classe Test
                        mainWindow.dispose(); // Fechando a janela mainWindow
                        mainWindow = null;
                        AddRandomTestWindow addRandomTestWindow = new AddRandomTestWindow(newTest, Integer.parseInt(entryTestNumber.getText()));
                    } catch (NumberFormatException ex) {
                        // Tratar o caso em que não é um número inteiro
                        JOptionPane.showMessageDialog(null, "O número de questões deve ser um valor inteiro.");
                    }
                }
            }
        });

        // Adição da ação de chamada da janela de cadastramento de questões na DB
        buttonAddQuestions.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showWindow(false);
                mainWindow.dispose();
                mainWindow = null;
                AddQuestionWindow addQuestionWindow = new AddQuestionWindow();
            }
        });

        // Adição da ação de chamada da janela de visualização e exclusão de questões
        buttonDelQuestions.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showWindow(false);
                mainWindow.dispose();
                mainWindow = null;
                
                QuestionListWindow questionListWindow = new QuestionListWindow();

            }
        });


    }

    // criação das labels
    public void createLabels() {
        JLabel labelQuestionRegistration = new JLabel("CADASTRO DE QUESTÕES");
        JLabel labelTestRegistration = new JLabel("CADASTRO DE AVALIAÇÃO");
        JLabel labelEntryTestsNumber = new JLabel("NUMERO DE AVALIAÇÕES");
        JLabel labelEntryInstitution = new JLabel("INSTITUIÇÃO");
        JLabel labelEntryEducadorName = new JLabel("PROFESSOR");
        JLabel labelEntrySchoolSubject = new JLabel("DISCIPLINA");
        JLabel labelTitle_SGAA = new JLabel("SGAA - SISTEMA GERENCIADOR DE AVALIAÇÕES ACADÊMICAS");
        ImageIcon originalIcon = new ImageIcon(loadImage("UEPBLOGO.png"));
        Image originalImage = originalIcon.getImage();
        int desiredWidth = 350;  // Largura desejada para a imagem
        int desiredHeight = 189;  // Altura desejada para a imagem
        Image resizedImage = originalImage.getScaledInstance(desiredWidth, desiredHeight, Image.SCALE_SMOOTH);
        ImageIcon resizedIcon = new ImageIcon(resizedImage);
        JLabel labelImageUEPB = new JLabel(resizedIcon);
        labelImageUEPB.setBounds(20, 200, desiredWidth, desiredHeight);
        mainWindow.add(labelImageUEPB);

        mainWindow.add(labelQuestionRegistration);
        mainWindow.add(labelTestRegistration);
        mainWindow.add(labelEntryTestsNumber);
        mainWindow.add(labelEntryInstitution);
        mainWindow.add(labelEntryEducadorName);
        mainWindow.add(labelEntrySchoolSubject);
        mainWindow.add(labelTitle_SGAA);
        

        labelQuestionRegistration.setBounds(110, 65, 165, 25);
        labelTestRegistration.setBounds(475, 65, 165, 25);
        labelEntryTestsNumber.setBounds(405, 110, 155, 25);
        labelEntryInstitution.setBounds(405, 150, 90, 25);
        labelEntryEducadorName.setBounds(405, 190, 90, 25);
        labelEntrySchoolSubject.setBounds(405, 230, 100, 25);
        labelTitle_SGAA.setBounds(70, 10, 435, 25);
        

    }

    // Pega o valor de todos os campos de entrada do usuário e cria um objeto da Classe Test para retornar
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

    // Apresentação da janela
    public void showWindow(boolean value){
        mainWindow.setVisible(value);
    }

    // Instanciamento da imagem logo do programa na janela principal
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
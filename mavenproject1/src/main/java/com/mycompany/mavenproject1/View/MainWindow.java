package com.mycompany.mavenproject1.View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.border.LineBorder;

import com.mycompany.mavenproject1.Model.Question;
import com.mycompany.mavenproject1.Model.Avaliacao;

public class MainWindow extends JPanel implements Window {

    private Avaliacao newTest;
    private JFrame mainWindow;
    private JTextField entryTestNumber;
    private JTextField entrySchoolSubject;
    private JTextField entryInstitution;
    private JTextField entryEducatorName;

    // CONSTRUCTOR
    public MainWindow() {

        // Initialize the JFrame and configure its properties
        mainWindow = new JFrame();
        mainWindow.setLayout(null);
        mainWindow.setSize(754, 427);
        mainWindow.setUndecorated(true);
        mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainWindow.setResizable(false);
        mainWindow.setLocationRelativeTo(null);

        // Create buttons, labels, text fields, and visual squares
        createButtons();
        createLabels();
        createTextFields();
        createVisualSquares();
        showWindow(true);
    }

    // Create visual squares for better aesthetics
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

    // Create text fields for user input
    public void createTextFields() {
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

    // Implementation of the createButtons method from the Window interface
    @Override
    public void createButtons() {

        // Create buttons with tooltips and set focusability
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

        // Add tooltips to the buttons
        buttonAddQuestions.setToolTipText("CLIQUE PARA ADICIONAR UMA NOVA QUESTÃO AO BANCO DE QUESTÕES.");
        buttonDelQuestions.setToolTipText("CLIQUE PARA VISUALIZAR OU DELETAR QUESTÕES DO BANCO DE QUESTÕES.");
        buttonClear.setToolTipText("CLIQUE PARA LIMPAR TODAS AS CAIXAS DE ENTRADA.");
        buttonGenerateRandomTest.setToolTipText("CLIQUE PARA GERAR UMA AVALIAÇÃO ALEATORIAMENTE.");
        buttonGenerateManualTest.setToolTipText("CLIQUE PARA GERAR MANUALMENTE UMA AVALIAÇÃO.");
        buttonClose.setToolTipText("CLIQUE PARA FECHAR O PROGRAMA");

        // Add buttons to the main window
        mainWindow.add(buttonAddQuestions);
        mainWindow.add(buttonDelQuestions);
        mainWindow.add(buttonClear);
        mainWindow.add(buttonGenerateRandomTest);
        mainWindow.add(buttonGenerateManualTest);
        mainWindow.add(buttonClose);

        // Set the bounds of each button
        buttonAddQuestions.setBounds(70, 110, 240, 30);
        buttonDelQuestions.setBounds(70, 160, 240, 30);
        buttonClear.setBounds(405, 325, 100, 30);
        buttonGenerateRandomTest.setBounds(555, 270, 190, 30);
        buttonGenerateManualTest.setBounds(555, 325, 190, 30);
        buttonClose.setBounds(645, 10, 100, 25);

        // Add action listeners to the buttons
        buttonClose.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        buttonClear.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Clear the text fields
                entryEducatorName.setText("");
                entryInstitution.setText("");
                entryTestNumber.setText("");
                entrySchoolSubject.setText("");
            }
        });

        buttonGenerateManualTest.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Check if required fields are not empty and handle number format exception
                if (!entryInstitution.getText().isEmpty() && !entrySchoolSubject.getText().isEmpty()
                        && !entryEducatorName.getText().isEmpty() && !entryTestNumber.getText().isEmpty()) {
                    try {
                        int testsNumber = Integer.parseInt(entryTestNumber.getText());
                        Avaliacao newTest = getDataFromEntries();
                        // Dispose of the main window and open the SelectQuestionWindow
                        mainWindow.dispose();
                        mainWindow = null;
                        SelectQuestionWindow selectQuestionWindow = new SelectQuestionWindow(newTest,
                                Integer.parseInt(entryTestNumber.getText()), entrySchoolSubject.getText().toLowerCase());
                    } catch (NumberFormatException ex) {
                        // Handle the case where the number is not an integer
                        JOptionPane.showMessageDialog(null, "O número de questões deve ser um valor inteiro.");
                    }
                }
            }
        });

        buttonGenerateRandomTest.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Check if required fields are not empty and handle number format exception
                if (!entryInstitution.getText().isEmpty() && !entrySchoolSubject.getText().isEmpty()
                        && !entryEducatorName.getText().isEmpty() && !entryTestNumber.getText().isEmpty()) {
                    try {
                        int testsNumber = Integer.parseInt(entryTestNumber.getText());
                        Avaliacao newTest = getDataFromEntries();
                        // Dispose of the main window and open the AddRandomTestWindow
                        mainWindow.dispose();
                        mainWindow = null;
                        AddRandomTestWindow addRandomTestWindow = new AddRandomTestWindow(newTest,
                                Integer.parseInt(entryTestNumber.getText()));
                    } catch (NumberFormatException ex) {
                        // Handle the case where the number is not an integer
                        JOptionPane.showMessageDialog(null, "O número de questões deve ser um valor inteiro.");
                    }
                }
            }
        });

        buttonAddQuestions.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Hide the main window and open the AddQuestionWindow
                showWindow(false);
                mainWindow.dispose();
                mainWindow = null;
                AddQuestionWindow addQuestionWindow = new AddQuestionWindow();
            }
        });

        buttonDelQuestions.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Hide the main window and open the QuestionListWindow
                showWindow(false);
                mainWindow.dispose();
                mainWindow = null;
                QuestionListWindow questionListWindow = new QuestionListWindow();
            }
        });
    }

    // Implementation of the createLabels method from the Window interface
    @Override
    public void createLabels() {
        // Create labels, including the university logo
        JLabel labelQuestionRegistration = new JLabel("CADASTRO DE QUESTÕES");
        JLabel labelTestRegistration = new JLabel("CADASTRO DE AVALIAÇÃO");
        JLabel labelEntryTestsNumber = new JLabel("NUMERO DE AVALIAÇÕES");
        JLabel labelEntryInstitution = new JLabel("INSTITUIÇÃO");
        JLabel labelEntryEducatorName = new JLabel("PROFESSOR");
        JLabel labelEntrySchoolSubject = new JLabel("DISCIPLINA");
        JLabel labelTitle_SGAA = new JLabel("SGAA - SISTEMA GERENCIADOR DE AVALIAÇÕES ACADÊMICAS");
        ImageIcon originalIcon = new ImageIcon(loadImage("/static/LOGO.png"));
        Image originalImage = originalIcon.getImage();
        int desiredWidth = 350; // Desired width for the image
        int desiredHeight = 189; // Desired height for the image
        Image resizedImage = originalImage.getScaledInstance(desiredWidth, desiredHeight, Image.SCALE_SMOOTH);
        ImageIcon resizedIcon = new ImageIcon(resizedImage);
        JLabel labelImageUEPB = new JLabel(resizedIcon);
        labelImageUEPB.setBounds(20, 200, desiredWidth, desiredHeight);
        mainWindow.add(labelImageUEPB);

        // Add labels to the main window
        mainWindow.add(labelQuestionRegistration);
        mainWindow.add(labelTestRegistration);
        mainWindow.add(labelEntryTestsNumber);
        mainWindow.add(labelEntryInstitution);
        mainWindow.add(labelEntryEducatorName);
        mainWindow.add(labelEntrySchoolSubject);
        mainWindow.add(labelTitle_SGAA);

        // Set the bounds of each label
        labelQuestionRegistration.setBounds(110, 65, 165, 25);
        labelTestRegistration.setBounds(475, 65, 165, 25);
        labelEntryTestsNumber.setBounds(405, 110, 155, 25);
        labelEntryInstitution.setBounds(405, 150, 90, 25);
        labelEntryEducatorName.setBounds(405, 190, 90, 25);
        labelEntrySchoolSubject.setBounds(405, 230, 100, 25);
        labelTitle_SGAA.setBounds(70, 10, 435, 25);
    }

    // Method to retrieve data from text fields and create a Test object
    public Avaliacao getDataFromEntries() {
        ArrayList<Question> questionsList = new ArrayList<>();
        newTest = new Avaliacao(entryInstitution.getText(), entrySchoolSubject.getText(), entryEducatorName.getText(),
                Integer.parseInt(entryTestNumber.getText()), questionsList);
        return newTest;
    }

    // Method to show or hide the main window
    public void showWindow(boolean value) {
        mainWindow.setVisible(value);
    }

    // Method to load an image from the resources
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
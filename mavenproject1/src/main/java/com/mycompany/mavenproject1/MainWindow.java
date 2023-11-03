package com.mycompany.mavenproject1;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;
import javax.swing.border.LineBorder;

public class MainWindow extends JPanel {


    private JFrame mainWindow;


    public MainWindow() {

        mainWindow = new JFrame();
        mainWindow.setLayout(null);
        mainWindow.setSize(754, 427);
        mainWindow.setUndecorated(true);
        mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainWindow.setResizable(false);
        mainWindow.setLocationRelativeTo(null);

        instaciarBotoes();
        instanciarLabels();
        instanciarTexFields();
        gerarQuadradosVisuais();
        mostrarJanela(true);
    }

    public void gerarQuadradosVisuais() {
        JPanel rightPanel = new JPanel();
        JPanel leftPanel = new JPanel();
        rightPanel.setBounds(377, 50, 377, 330);
        rightPanel.setBorder(new LineBorder(Color.BLACK));
        mainWindow.add(rightPanel);
        leftPanel.setBounds(0, 50, 754, 330);
        leftPanel.setBorder(new LineBorder(Color.BLACK));
        mainWindow.add(leftPanel);
    }

    public void instanciarTexFields() {

        JTextField entryNumquestoes = new JTextField(5);
        JTextField entryDisciplina = new JTextField(5);
        JTextField entryInstituicao = new JTextField(5);
        JTextField entryProfessor = new JTextField(5);

        mainWindow.add(entryNumquestoes);
        mainWindow.add(entryDisciplina);
        mainWindow.add(entryInstituicao);
        mainWindow.add(entryProfessor);

        entryNumquestoes.setBounds(555, 110, 185, 25);
        entryDisciplina.setBounds(495, 230, 245, 25);
        entryInstituicao.setBounds(495, 150, 245, 25);
        entryProfessor.setBounds(495, 190, 245, 25);

    }

    public void instaciarBotoes() {

        JButton buttonAddQuestoes = new JButton("ADICIONAR QUESTÕES");
        JButton buttonDelQuestoes = new JButton("DELETAR/PESQUISAR QUESTÕES");
        JButton buttonLimpar = new JButton("LIMPAR");
        JButton buttonGerarAvAleatoria = new JButton("GERAR PROVA ALEATÓRIA");
        JButton buttonProvaManual = new JButton("GERAR PROVA MANUAL");
        JButton buttonClose = new JButton("FECHAR");

        buttonAddQuestoes.setFocusable(false);
        buttonDelQuestoes.setFocusable(false);
        buttonLimpar.setFocusable(false);
        buttonGerarAvAleatoria.setFocusable(false);
        buttonProvaManual.setFocusable(false);
        buttonClose.setFocusable(false);

        buttonAddQuestoes.setToolTipText("CLIQUE PARA ADICIONAR UMA NOVA QUESTÃO AO BANCO DE QUESTÕES.");
        buttonDelQuestoes.setToolTipText("CLIQUE PARA VIZUALIZAR QUESTÕES OU DELETAR QUESTÕES DO BANCO DE QUESTÕES.");
        buttonLimpar.setToolTipText("CLIQUE PARA LIMPAR TODAS AS CAIXAS DE ENTRADA.");
        buttonGerarAvAleatoria.setToolTipText("CLIQUE PARA GERAR UMA AVALIAÇÃO ALEATORIAMENTE.");
        buttonProvaManual.setToolTipText("CLIQUE PARA GERAR MANUALMENTE UMA AVALIAÇÃO.");
        buttonClose.setToolTipText("CLIQUE PARA FECHAR O PROGRAMA");

        mainWindow.add(buttonAddQuestoes);
        mainWindow.add(buttonDelQuestoes);
        mainWindow.add(buttonLimpar);
        mainWindow.add(buttonGerarAvAleatoria);
        mainWindow.add(buttonProvaManual);
        mainWindow.add(buttonClose);


        buttonAddQuestoes.setBounds(70, 110, 240, 30);
        buttonDelQuestoes.setBounds(70, 160, 240, 30);
        buttonLimpar.setBounds(405, 325, 100, 30);
        buttonGerarAvAleatoria.setBounds(555, 270, 190, 30);
        buttonProvaManual.setBounds(555, 325, 190, 30);
        buttonClose.setBounds(645, 10, 100, 25);

        buttonClose.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        buttonAddQuestoes.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mostrarJanela(false);
                mainWindow.dispose();
                mainWindow = null;
                AddQuestionWindow addQuestionWindow = new AddQuestionWindow();
            }
        });

        buttonDelQuestoes.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mostrarJanela(false);
                mainWindow.dispose();
                mainWindow = null;
                QuestionListWindow questionListWindow = new QuestionListWindow();

            }
        });

    }

    public void instanciarLabels() {
        JLabel labelCadQuestoes = new JLabel("CADASTRO DE QUESTÕES");
        JLabel labelCadAvalicao = new JLabel("CADASTRO DE AVALIAÇÃO");
        JLabel labelEntryNumQuestoes = new JLabel("NUMERO DE QUESTÕES");
        JLabel labelEntryInstituicao = new JLabel("INSTITUIÇÃO");
        JLabel labelEntryProfessor = new JLabel("PROFESSOR");
        JLabel labelEntryDisciplina = new JLabel("DISCIPLINA");
        JLabel labelPrincipal_SGAA = new JLabel("SGGA - SISTEMA GERENCIADOR DE AVALIAÇÕES ACADÊMICAS");
        ImageIcon icon = new ImageIcon(loadImage("UEPBLOGO.png"));//COLOCAR CASO DE ERRO PARA QUANDO NÃO CARREGAR A IMAGEM
        JLabel labelImageUEPB = new JLabel(icon);

        mainWindow.add(labelCadQuestoes);
        mainWindow.add(labelCadAvalicao);
        mainWindow.add(labelEntryNumQuestoes);
        mainWindow.add(labelEntryInstituicao);
        mainWindow.add(labelEntryProfessor);
        mainWindow.add(labelEntryDisciplina);
        mainWindow.add(labelPrincipal_SGAA);
        mainWindow.add(labelImageUEPB);

        labelCadQuestoes.setBounds(110, 65, 165, 25);
        labelCadAvalicao.setBounds(475, 65, 165, 25);
        labelEntryNumQuestoes.setBounds(405, 110, 150, 25);
        labelEntryInstituicao.setBounds(405, 150, 90, 25);
        labelEntryProfessor.setBounds(405, 190, 90, 25);
        labelEntryDisciplina.setBounds(405, 230, 100, 25);
        labelPrincipal_SGAA.setBounds(70, 10, 435, 25);
        labelImageUEPB.setBounds(20, 220, 350, 158);

    }

    public void mostrarJanela(boolean valor){
        mainWindow.setVisible(valor);
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
package com.mycompany.mavenproject1;


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;
import javax.swing.border.LineBorder;

public class QuestionManagerWindow extends JPanel {

    private JPanel leftPanel;
    private JPanel rightPanel;
    private JLabel labelCadQuestoes, labelCadAvalicao;
    private JButton buttonAddQuestoes, buttonDelQuestoes, buttonLimpar, buttonGerarAvAleatoria, buttonProvaManual, buttonClose;
    private JLabel labelEntryNumQuestoes, labelEntryInstituicao, labelEntryProfessor, labelEntryDisciplina;
    private JTextField entryNumquestoes, entryDisciplina, entryInstituicao, entryProfessor;
    private JLabel labelPrincipal_SGAA;
    private JLabel labelImageUEPB;
    private ImageIcon icon;
    private JFrame questionManagerWindow;



    public QuestionManagerWindow(JFrame mainWindow) {

        questionManagerWindow = new JFrame();
        questionManagerWindow.setSize(754, 427);
        questionManagerWindow.setUndecorated(true);
        questionManagerWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        questionManagerWindow.setResizable(false);
        questionManagerWindow.setLocationRelativeTo(null);

        instaciarBotoes();
        mostrarJanela(true);

    }

    public void instaciarBotoes() {
        buttonAddQuestoes = new JButton("ADICIONAR QUESTÕES");
        buttonDelQuestoes = new JButton("DELETAR/PESQUISAR QUESTÕES");
        buttonLimpar = new JButton("LIMPAR");
        buttonGerarAvAleatoria = new JButton("GERAR PROVA ALEATÓRIA");
        buttonProvaManual = new JButton("GERAR PROVA MANUAL");
        buttonClose = new JButton("FECHAR");
        buttonAddQuestoes.setToolTipText("CLIQUE PARA ADICIONAR UMA NOVA QUESTÃO AO BANCO DE QUESTÕES.");
        buttonDelQuestoes.setToolTipText("CLIQUE PARA VIZUALIZAR QUESTÕES OU DELETAR QUESTÕES DO BANCO DE QUESTÕES.");
        buttonLimpar.setToolTipText("CLIQUE PARA LIMPAR TODAS AS CAIXAS DE ENTRADA.");
        buttonGerarAvAleatoria.setToolTipText("CLIQUE PARA GERAR UMA AVALIAÇÃO ALEATORIAMENTE.");
        buttonProvaManual.setToolTipText("CLIQUE PARA GERAR MANUALMENTE UMA AVALIAÇÃO.");
        buttonClose.setToolTipText("CLIQUE PARA FECHAR O PROGRAMA");

        questionManagerWindow.add(buttonAddQuestoes);
        questionManagerWindow.add(buttonDelQuestoes);
        questionManagerWindow.add(buttonLimpar);
        questionManagerWindow.add(buttonGerarAvAleatoria);
        questionManagerWindow.add(buttonProvaManual);
        questionManagerWindow.add(buttonClose);


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

        buttonDelQuestoes.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mostrarJanela(false);
                QuestionListWindow questionListWindow = new QuestionListWindow(questionManagerWindow);

            }
        });

    }

    public void mostrarJanela(boolean valor){
        questionManagerWindow.setVisible(valor);
    }
}
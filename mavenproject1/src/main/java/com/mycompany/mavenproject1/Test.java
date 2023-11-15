package com.mycompany.mavenproject1;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JOptionPane;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

public class Test {

    // Atributos da classe
    private String institution;
    private String schoolSubject;
    private String educatorName;
    private int testsNumber;
    private ArrayList<Question> questionsList;

    // Método para randomizar a ordem das questões da avaliação
    public ArrayList<Question> randomizeQuestions(ArrayList<Question> listQuestions, int easyQuantQuestions,
            int moderateQuantQuestions, int hardQuantQuestions) {

        ArrayList<Question> selectedQuestions = new ArrayList<>();
        ArrayList<Question> receivedQuestions = new ArrayList<>(listQuestions); // Clone da lista
        Random random = new Random(); // Instanciando um objeto Random

        // Verificando se a quantidade das questões existentes atendem à quantidade pedida de cada uma das dificuldades
        if (countQuestionsWithDifficulty(receivedQuestions, 1) < easyQuantQuestions ||
                countQuestionsWithDifficulty(receivedQuestions, 2) < moderateQuantQuestions ||
                countQuestionsWithDifficulty(receivedQuestions, 3) < hardQuantQuestions) {

            JOptionPane.showMessageDialog(null, 
            "A quantidade de questões informadas por nível difere da quantidade presente no banco de questões. Verifique o banco de questões e tente novamente.");

            throw new IllegalArgumentException("Quantidade de questões excede o existente no banco de dados!");

        }

        //Essas três seções de código são responsáveis por selecionar questões aleatórias de cada nível de dificuldade        
        for (int i = 0; i < easyQuantQuestions; i++) {
            int index = getRandomIndex(random, receivedQuestions, 1);
            selectedQuestions.add(receivedQuestions.get(index));
            receivedQuestions.remove(index);
        }

        for (int i = 0; i < moderateQuantQuestions; i++) {
            int index = getRandomIndex(random, receivedQuestions, 2);
            selectedQuestions.add(receivedQuestions.get(index));
            receivedQuestions.remove(index);
        }

        for (int i = 0; i < hardQuantQuestions; i++) {
            int index = getRandomIndex(random, receivedQuestions, 3);
            selectedQuestions.add(receivedQuestions.get(index));
            receivedQuestions.remove(index);
        }

        // Embaralhando as questões
        Collections.shuffle(selectedQuestions);

        return selectedQuestions;
    }

    // Conta a quantidade de questões da lista recebida com base na dificuldade
    private int countQuestionsWithDifficulty(ArrayList<Question> questions, int difficulty) {
        int count = 0;
        for (Question question : questions) {
            if (question.getDifficult() == difficulty) {
                count++;
            }
        }
        return count;
    }

    // Esse método getRandomIndex é utilizado para obter um índice aleatório de uma questão com uma dificuldade específica da lista de questões
    private int getRandomIndex(Random random, ArrayList<Question> questions, int difficulty) {
        int index;
        do {
            index = random.nextInt(questions.size());
        } while (questions.get(index).getDifficult() != difficulty);
        return index;
    }

    // Método de geração dos arquivos do tipo .docx aleatório
    public void generateFile(ArrayList<Question> listQuestions, int easyQuantQuestions, int moderateQuantQuestions,
            int hardQuantQuestions, String directory, int testsNumber, boolean acceptOpenedQuestions) {

        FileOutputStream out = null;
        XWPFDocument document = new XWPFDocument();

        // For para crair a quantidade de testes diferentes correspondentes ao que foi pedido
        for (int newTestNumber = 0; newTestNumber < testsNumber; newTestNumber++) {

            // Escrevendo o arquivo
            try {
                document = new XWPFDocument(); // Iniciando o documento
                String myFile = "Prova" + newTestNumber + ".docx"; // Nome do documento baseado no seu número de geração
                java.io.File file = new java.io.File(directory, myFile); // Configurando o arquivo com seu diret´rorio de saída e o arquivo
                out = new FileOutputStream(file); // Escrevendo no arquivo especificado

                XWPFParagraph paragrafo = document.createParagraph(); // Criação do parágrafo
                XWPFRun runPaRun1 = paragrafo.createRun(); // Sequência contígua de caracteres com as mesmas propriedades de formatação.

                runPaRun1.addBreak(); // Quenbra de linha
                runPaRun1.setText("_________________________________________________________________________",
                        0); // Texto a escrever
                runPaRun1.addBreak();
                runPaRun1.addBreak();
                runPaRun1.addTab();
                runPaRun1.setText("Instituição: " + institution, 1);
                runPaRun1.addBreak();
                runPaRun1.addTab();
                runPaRun1.setText("Professor: " + educatorName, 2);
                runPaRun1.addBreak();
                runPaRun1.addTab();
                runPaRun1.setText(
                        "Aluno: .....................................................................................................",
                        3);
                runPaRun1.addBreak();
                runPaRun1.addBreak();
                runPaRun1.addTab();
                runPaRun1.setText("Matrícula: ............................. \t\t\t\t\t Nota: .............", 4);
                runPaRun1.addBreak();
                runPaRun1.removeTab();
                runPaRun1.setText("_________________________________________________________________________",
                        5);
                runPaRun1.addBreak();
                runPaRun1.addBreak();
                runPaRun1.setBold(true); // Colocando em negrito

                XWPFParagraph paragrafo2 = document.createParagraph(); // Criação de novo parágrafo
                XWPFRun runPaRun2 = paragrafo2.createRun(); // Nova sequência contígua de caracteres, mas com nova formatação

                // Lista de questões
                ArrayList<Question> selectedQuestions;

                // Verificando se deve aceitar questões abertas
                if (acceptOpenedQuestions == true) {

                    selectedQuestions = randomizeQuestions(removeOpenedQuestions(listQuestions),
                            easyQuantQuestions,
                            moderateQuantQuestions, hardQuantQuestions);

                } else {

                    selectedQuestions = randomizeQuestions(listQuestions, easyQuantQuestions,
                            moderateQuantQuestions, hardQuantQuestions);

                }

                // Varre a lista de questões e escreve as questões uma a uma
                for (int i = 0; i < selectedQuestions.size(); i++) {
                    runPaRun2.addTab();
                    runPaRun2.setText(Integer.toString(i + 1) + "°) ");
                    runPaRun2.setText(selectedQuestions.get(i).getQuestion());
                    runPaRun2.addBreak();
                    runPaRun2.addBreak();

                    // Lista de itens da questão
                    ArrayList<String> alternatives = selectedQuestions.get(i).getItems();

                    // Escreve os itens da questão um a um se houver
                    for (int j = 0; j < alternatives.size(); j++) {
                        String alternativeText = alternatives.get(j);

                        if (alternativeText != null && !alternativeText.trim().isEmpty()) {

                            char letter = (char) ('A' + j);

                            runPaRun2.addBreak();
                            runPaRun2.addTab();
                            runPaRun2.addTab();
                            runPaRun2.setText(letter + ") " + alternativeText);
                            runPaRun2.addBreak();
                            runPaRun2.addBreak();
                            runPaRun2.addBreak();
                        }
                    }
                }

            // Tratamento de erros
            } catch (FileNotFoundException ex) {

                Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ex);

            } catch (IOException ex) {

                Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ex);

            } finally {

                // concluindo o processo
                try {
                    // Verificando se a saida não é nula
                    if (out != null) {
                        document.write(out); // Escrevendo o arquivo e baixando
                        out.close(); // Fechando o documento atual
                    }
                } catch (IOException ex) {
                    Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ex);
                }

            }

        }

    }

    // Método de geração dos arquivos do tipo .docx com as questões escolhidas manualmente.
    // Obs: Como o método anterior faz o mesmo processo, apenas não validando a quantidade de cada tipo de questão não irei comentar essa
    public void generateFile(ArrayList<Question> listQuestions, String directory, int testsNumber) {

        FileOutputStream out = null;
        XWPFDocument document = new XWPFDocument();

        for (int newTestNumber = 0; newTestNumber < testsNumber; newTestNumber++) {

            try {
                document = new XWPFDocument();
                String myFile = "Prova" + newTestNumber + ".docx";
                java.io.File file = new java.io.File(directory, myFile);
                out = new FileOutputStream(file);

                XWPFParagraph paragrafo = document.createParagraph();
                XWPFRun runPaRun1 = paragrafo.createRun();

                runPaRun1.addBreak();
                runPaRun1.setText("_________________________________________________________________________",
                        0);
                runPaRun1.addBreak();
                runPaRun1.addBreak();
                runPaRun1.addTab();
                runPaRun1.setText("Instituição: " + institution, 1);
                runPaRun1.addBreak();
                runPaRun1.addTab();
                runPaRun1.setText("Professor: " + educatorName, 2);
                runPaRun1.addBreak();
                runPaRun1.addTab();
                runPaRun1.setText(
                        "Aluno: ............................................................................................................",
                        3);
                runPaRun1.addBreak();
                runPaRun1.addBreak();
                runPaRun1.addTab();
                runPaRun1.setText("Matrícula: ............................. \t\t\t\t\t Nota: .............", 4);
                runPaRun1.addBreak();
                runPaRun1.removeTab();
                runPaRun1.setText("_________________________________________________________________________",
                        5);
                runPaRun1.addBreak();
                runPaRun1.addBreak();
                runPaRun1.setBold(true);

                XWPFParagraph paragrafo2 = document.createParagraph();
                XWPFRun runPaRun2 = paragrafo2.createRun();

                for (int i = 0; i < listQuestions.size(); i++) {
                    runPaRun2.addTab();
                    runPaRun2.setText(Integer.toString(i + 1) + "°) ");
                    runPaRun2.setText(listQuestions.get(i).getQuestion());
                    runPaRun2.addBreak();

                    ArrayList<String> alternatives = listQuestions.get(i).getItems();

                    for (int j = 0; j < alternatives.size(); j++) {
                        String alternativeText = alternatives.get(j);

                        if (alternativeText != null && !alternativeText.trim().isEmpty()) {

                            char letter = (char) ('A' + j);

                            runPaRun2.addBreak();
                            runPaRun2.addTab();
                            runPaRun2.addTab();
                            runPaRun2.setText(letter + ") " + alternativeText);
                            runPaRun2.addBreak();
                            runPaRun2.addBreak();
                            runPaRun2.addBreak();
                        }
                    }
                }

            } catch (FileNotFoundException ex) {

                Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ex);

            } catch (IOException ex) {

                Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ex);

            } finally {

                try {
                    if (out != null) {
                        document.write(out);
                        out.close();
                    }
                } catch (IOException ex) {
                    Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ex);
                }

            }

        }

    }

    // Método para remover da lista as questões de tipo aberta
    public ArrayList<Question> removeOpenedQuestions(ArrayList<Question> questions) {

        ArrayList<Question> newSelectedQuestions = new ArrayList<Question>();

        // Varre a lista de questões
        for (int i = 0; i < questions.size(); i++) {

            // Verifica cada uma das questões se não tem seu item na posição 0 como nulo ou como vazio
            if (questions.get(i).getItems().get(0) != null && !questions.get(i).getItems().get(0).trim().isEmpty()) {

                newSelectedQuestions.add(questions.get(i));

            }

        }

        return newSelectedQuestions;

    }

    // Método construtor
    public Test(String institution, String schoolSubject, String educatorName, int testsNumber,
            ArrayList<Question> questionsList) {
        this.institution = institution;
        this.schoolSubject = schoolSubject;
        this.educatorName = educatorName;
        this.testsNumber = testsNumber;
        this.questionsList = questionsList;
    }

    // Métodos getters e setters
    public ArrayList<Question> getQuestionsList() {
        return questionsList;
    }

    public void setQuestionsList(ArrayList<Question> questionsList) {
        this.questionsList = questionsList;
    }

    public String getInstitution() {
        return institution;
    }

    public void setInstitution(String institution) {
        this.institution = institution;
    }

    public String getSchoolSubject() {
        return schoolSubject;
    }

    public void setSchoolSubject(String schoolSubject) {
        this.schoolSubject = schoolSubject;
    }

    public String getEducatorName() {
        return educatorName;
    }

    public void setEducatorName(String educatorName) {
        this.educatorName = educatorName;
    }

    public int getTestsNumber() {
        return testsNumber;
    }

    public void setTestsNumber(int testsNumber) {
        this.testsNumber = testsNumber;
    }

    // Sobrescrita do método tostring
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Instituição: ").append(institution).append("\n");
        sb.append("Disciplina: ").append(schoolSubject).append("\n");
        sb.append("Professor: ").append(educatorName).append("\n");
        sb.append("---------------------------------------------------------------\n");
        for (int i = 0; i < questionsList.size(); i++) {
            sb.append("\n").append(i + 1).append(") ").append(questionsList.get(i));
        }
        return sb.toString();
    }

}

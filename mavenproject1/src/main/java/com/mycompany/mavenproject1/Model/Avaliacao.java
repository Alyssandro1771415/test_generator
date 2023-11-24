package com.mycompany.mavenproject1.Model;

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

public class Avaliacao {

    private String institution;
    private String schoolSubject;
    private String educatorName;
    private int testsNumber;
    private ArrayList<Question> questionsList;
    public boolean quantityOK;

    // Method to randomize a ArrayList of objects of Question type
    public ArrayList<Question> randomizeQuestions(ArrayList<Question> listQuestions, int easyQuantQuestions,
            int moderateQuantQuestions, int hardQuantQuestions) {

        ArrayList<Question> selectedQuestions = new ArrayList<>();
        ArrayList<Question> receivedQuestions = new ArrayList<>(listQuestions); // Clone da lista
        Random random = new Random();

        if (countQuestionsWithDifficulty(receivedQuestions, 1) < easyQuantQuestions ||
                countQuestionsWithDifficulty(receivedQuestions, 2) < moderateQuantQuestions ||
                countQuestionsWithDifficulty(receivedQuestions, 3) < hardQuantQuestions) {

            JOptionPane.showMessageDialog(null, 
            "A quantidade de questões informadas por nível difere da quantidade presente no banco de questões. Verifique o banco de questões e tente novamente.");
            quantityOK = false;
            throw new IllegalArgumentException("Quantidade de questões excede o existente no banco de dados!");

        }
        else {
            quantityOK = true;
        }
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

        Collections.shuffle(selectedQuestions);

        return selectedQuestions;
    }

    // Return a true to confirm that a needed quantity of questions corresponds with what was requested
    public boolean getQuantityOK(){
        return this.quantityOK;
    }

    // Count the quantity of objects of Question type exist in a ArrayList of the difficulty solicited
    public int countQuestionsWithDifficulty(ArrayList<Question> questions, int difficulty) {
        int count = 0;
        for (Question question : questions) {
            if (question.getDifficult() == difficulty) {
                count++;
            }
        }
        return count;
    }

    /*
     In summary, this method is used to randomly select an index of a question from an ArrayList based on a 
     specified difficulty level, and it ensures that the selected question matches the desired difficulty 
     before returning the index.
    */
    public int getRandomIndex(Random random, ArrayList<Question> questions, int difficulty) {
        int index;
        do {
            index = random.nextInt(questions.size());
        } while (questions.get(index).getDifficult() != difficulty);
        return index;
    }

    /* 
    This method is used to generate a avaliation file and download it in a selected directory, 
    but in this method is informed the quantity of easy, moderate and hard question is to be used
     */
    public void generateFile(ArrayList<Question> listQuestions, int easyQuantQuestions, int moderateQuantQuestions,
            int hardQuantQuestions, String directory, int testsNumber, boolean acceptOpenedQuestions) {

        FileOutputStream out = null;
        XWPFDocument document = new XWPFDocument();

        for (int newTestNumber = 0; newTestNumber < testsNumber; newTestNumber++) {

            try {
                ArrayList<Question> selectedQuestions;

                if (acceptOpenedQuestions == true) {

                    selectedQuestions = randomizeQuestions(removeOpenedQuestions(listQuestions),
                            easyQuantQuestions,
                            moderateQuantQuestions, hardQuantQuestions);

                } else {

                    selectedQuestions = randomizeQuestions(listQuestions, easyQuantQuestions,
                            moderateQuantQuestions, hardQuantQuestions);

                }
                if (quantityOK) {
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
                            "Aluno: .....................................................................................................",
                            3);
                    runPaRun1.addBreak();
                    runPaRun1.addBreak();
                    runPaRun1.addTab();
                    runPaRun1.setText("Matrícula: ............................. \t\t\t\t\t Nota: .............   Data: __/__/____", 4);
                    runPaRun1.addBreak();
                    runPaRun1.removeTab();
                    runPaRun1.setText("_________________________________________________________________________",
                            5);
                    runPaRun1.addBreak();
                    runPaRun1.addBreak();
                    runPaRun1.setBold(true);

                    XWPFParagraph paragrafo2 = document.createParagraph();
                    XWPFRun runPaRun2 = paragrafo2.createRun();


                    for (int i = 0; i < selectedQuestions.size(); i++) {
                        runPaRun2.addTab();
                        runPaRun2.setText(Integer.toString(i + 1) + "°) ");
                        runPaRun2.setText(selectedQuestions.get(i).getQuestion());
                        runPaRun2.addBreak();
                        runPaRun2.addBreak();

                        ArrayList<String> alternatives = selectedQuestions.get(i).getItems();

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
                }

            } catch (FileNotFoundException ex) {

                Logger.getLogger(Avaliacao.class.getName()).log(Level.SEVERE, null, ex);

            } catch (IOException ex) {

                Logger.getLogger(Avaliacao.class.getName()).log(Level.SEVERE, null, ex);

            } finally {

                try {
                    if (out != null) {
                        document.write(out);
                        out.close();
                    }
                } catch (IOException ex) {
                    Logger.getLogger(Avaliacao.class.getName()).log(Level.SEVERE, null, ex);
                }

            }

        }

    }

    /* 
    This method is used to generate a avaliation file and download it in a selected directory,
    in this method the ArrayList has the user selected questions to be used in the file
     */
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
                runPaRun1.setText("Matrícula: ............................. \t\t\t\t\t Nota: .............   Data: __/__/____", 4);
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

                Logger.getLogger(Avaliacao.class.getName()).log(Level.SEVERE, null, ex);

            } catch (IOException ex) {

                Logger.getLogger(Avaliacao.class.getName()).log(Level.SEVERE, null, ex);

            } finally {

                try {
                    if (out != null) {
                        document.write(out);
                        out.close();
                    }
                } catch (IOException ex) {
                    Logger.getLogger(Avaliacao.class.getName()).log(Level.SEVERE, null, ex);
                }

            }

        }

    }

    /*
    This method remove the opened questions of a ArrayList when 
    */
    public ArrayList<Question> removeOpenedQuestions(ArrayList<Question> questions) {

        ArrayList<Question> newSelectedQuestions = new ArrayList<Question>();

        for (int i = 0; i < questions.size(); i++) {

            if (questions.get(i).getItems().get(0) != null && !questions.get(i).getItems().get(0).trim().isEmpty()) {

                newSelectedQuestions.add(questions.get(i));

            }

        }

        return newSelectedQuestions;

    }

    //Constructor of a Avaliation object 
    public Avaliacao(String institution, String schoolSubject, String educatorName, int testsNumber,
            ArrayList<Question> questionsList) {
        this.institution = institution;
        this.schoolSubject = schoolSubject;
        this.educatorName = educatorName;
        this.testsNumber = testsNumber;
        this.questionsList = questionsList;
    }

    // Getters and Setter
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

    // Override of the toString method to a better visualisation of the avaliation datas
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

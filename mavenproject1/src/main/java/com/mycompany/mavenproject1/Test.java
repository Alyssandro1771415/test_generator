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

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

public class Test {

    private String institution;
    private String schoolSubject;
    private String educatorName;
    private int testsNumber;
    private ArrayList<Question> questionsList;

    public ArrayList<Question> randomizeQuestions(ArrayList<Question> listQuestions, int easyQuantQuestions,
            int moderateQuantQuestions,
            int hardQuantQuestions) {

        ArrayList<Question> selectedQuestions = new ArrayList<>();
        Random random = new Random();

        for (int i = 0; i < easyQuantQuestions; i++) {
            if (listQuestions.size() > 0) {
                int index = random.nextInt(listQuestions.size());
                selectedQuestions.add(listQuestions.get(index));
                listQuestions.remove(index);
            }
        }
        for (int i = 0; i < moderateQuantQuestions; i++) {
            if (listQuestions.size() > 0) {
                int index = random.nextInt(listQuestions.size());
                selectedQuestions.add(listQuestions.get(index));
                listQuestions.remove(index);
            }
        }
        for (int i = 0; i < hardQuantQuestions; i++) {
            if (listQuestions.size() > 0) {
                int index = random.nextInt(listQuestions.size());
                selectedQuestions.add(listQuestions.get(index));
                listQuestions.remove(index);
            }
        }

        Collections.shuffle(selectedQuestions);

        return selectedQuestions;

    }

    public void generateFile(ArrayList<Question> listQuestions, int easyQuantQuestions, int moderateQuantQuestions,
            int hardQuantQuestions) {

        FileOutputStream out = null;
        XWPFDocument document = new XWPFDocument();
        ArrayList<Question> selectedQuestions = randomizeQuestions(listQuestions, easyQuantQuestions,
                moderateQuantQuestions, hardQuantQuestions);

        try {

            DirectorySelector selector = new DirectorySelector();
            String chossedDirectory = selector.directorySelector();    

            String myFile = "Prova.docx";
            java.io.File file = new java.io.File(chossedDirectory, myFile);
            out = new FileOutputStream(file);


            XWPFParagraph paragrafo = document.createParagraph();
            XWPFRun runPaRun1 = paragrafo.createRun();

            runPaRun1.addBreak();
            runPaRun1.setText("_________________________________________________________________________________", 0);
            runPaRun1.addBreak();
            runPaRun1.addBreak();
            runPaRun1.addTab();
            runPaRun1.setText("Instituição: " + institution, 1);
            runPaRun1.addBreak();
            runPaRun1.addTab();
            runPaRun1.setText("Professor: " + educatorName, 2);
            runPaRun1.addBreak();
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
            runPaRun1.setText("_________________________________________________________________________________", 5);
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

            document.write(out);
            document.close();

        } catch (FileNotFoundException ex) {
            Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException ex) {
                Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public Test(String institution, String schoolSubject, String educatorName, int testsNumber,
            ArrayList<Question> questionsList) {
        this.institution = institution;
        this.schoolSubject = schoolSubject;
        this.educatorName = educatorName;
        this.testsNumber = testsNumber;
        this.questionsList = questionsList;
    }

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

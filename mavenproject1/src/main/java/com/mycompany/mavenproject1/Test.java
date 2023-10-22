package com.mycompany.mavenproject1;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

public class Test {

    public void gerarArquivo(ArrayList<Question> listQuestions) {
        FileOutputStream out = null;
        XWPFDocument document = new XWPFDocument();

        try {
            out = new FileOutputStream(new File("Prova.docx"));

            XWPFParagraph paragrafo = document.createParagraph();
            XWPFRun runPaRun1 = paragrafo.createRun();

            runPaRun1.addBreak();
            runPaRun1.setText("_________________________________________________________________________________", 0);
            runPaRun1.addBreak();
            runPaRun1.addBreak();
            runPaRun1.addTab();
            runPaRun1.setText(
                    "Instituição: .....................................................................................................",
                    1);
            runPaRun1.addBreak();
            runPaRun1.addTab();
            runPaRun1.setText(
                    "Aluno: ............................................................................................................",
                    2);
            runPaRun1.addBreak();
            runPaRun1.addTab();
            runPaRun1.setText("Matrícula: ............................. \t\t\t\t\t Nota: .............", 3);
            runPaRun1.addBreak();
            runPaRun1.removeTab();
            runPaRun1.setText("_________________________________________________________________________________", 4);
            runPaRun1.addBreak();
            runPaRun1.addBreak();
            runPaRun1.setBold(true);

            XWPFParagraph paragrafo2 = document.createParagraph();
            XWPFRun runPaRun2 = paragrafo2.createRun();

            for (int i = 0; i <= listQuestions.size() - 1; i++) {

                runPaRun2.addTab();
                runPaRun2.setText(Integer.toString(i + 1) + "°) ");
                runPaRun2.setText(listQuestions.get(i).getQuestion());
                runPaRun2.addBreak();

                for (int j = 0; j <= (listQuestions.get(i).getItems()).size() - 1; j++) {

                    if ((listQuestions.get(i).getItems()).get(j) != null) {
                        char letter = (char) ('A' + j);

                        runPaRun2.addBreak();
                        runPaRun2.addTab();
                        runPaRun2.addTab();
                        runPaRun2.setText(letter + ") " + (listQuestions.get(i).getItems()).get(j));
                        runPaRun2.addBreak();
                        runPaRun2.addBreak();
                        runPaRun2.addBreak();
                    }

                }

                runPaRun2.addBreak();
                runPaRun2.addBreak();

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
}

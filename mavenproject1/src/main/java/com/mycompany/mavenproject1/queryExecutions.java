package com.mycompany.mavenproject1;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class queryExecutions {

    public ArrayList<Question> realizeConsult() {

        ArrayList<Question> myQuestions = new ArrayList<Question>();

        try {
            DatabaseManager dbManager = new DatabaseManager("jdbc:mysql://localhost/TestQuestionDB", "root", "");
            ResultSet dados = dbManager.executeQuery("SELECT * FROM questions");

            while (dados.next()) {
                Question questao = new Question(dados.getInt("id"), dados.getString("schoolSubject"),
                        dados.getString("content"), dados.getString("question"), dados.getInt("difficult"));
                myQuestions.add(questao);
            }

        } catch (SQLException ex) {
            System.out.println("Ocorreu um erro ao acessar a DB: " + ex.getMessage());
        }

        return myQuestions;

    }

    public void dataUpload(String schoolSubjString, String conteString, String questionString, int difficult,
            String itemA, String itemB, String itemC, String itemD, String itemE, String itemF) {
        try {
            DatabaseManager dbManager = new DatabaseManager("jdbc:mysql://localhost/TestQuestionDB", "root", "");

            String insertQuery = "INSERT INTO Questions (schoolSubject, content, question, difficult, itemA, itemB, itemC, itemD, itemE, itemF) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            int rowsAffected = dbManager.executeUpdate(insertQuery, schoolSubjString, conteString, questionString,
                    difficult, itemA, itemB, itemC, itemD, itemE, itemF);

            System.out.println("Linhas afetadas pela inserção: " + rowsAffected);

        } catch (SQLException ex) {
            System.out.println("Ocorreu um erro ao acessar a DB: " + ex.getMessage());
        }
    }

    public void dataUpload(String schoolSubjString, String conteString, String questionString, int difficult,
            String itemA, String itemB, String itemC, String itemD, String itemE) {
        try {
            DatabaseManager dbManager = new DatabaseManager("jdbc:mysql://localhost/TestQuestionDB", "root", "");

            String insertQuery = "INSERT INTO Questions (schoolSubject, content, question, difficult, itemA, itemB, itemC, itemD, itemE) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            int rowsAffected = dbManager.executeUpdate(insertQuery, schoolSubjString, conteString, questionString,
                    difficult, itemA, itemB, itemC, itemD, itemE);

            System.out.println("Linhas afetadas pela inserção: " + rowsAffected);

        } catch (SQLException ex) {
            System.out.println("Ocorreu um erro ao acessar a DB: " + ex.getMessage());
        }
    }

    public void dataUpload(String schoolSubjString, String conteString, String questionString, int difficult,
            String itemA, String itemB, String itemC, String itemD) {
        try {
            DatabaseManager dbManager = new DatabaseManager("jdbc:mysql://localhost/TestQuestionDB", "root", "");

            String insertQuery = "INSERT INTO Questions (schoolSubject, content, question, difficult, itemA, itemB, itemC, itemD) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

            int rowsAffected = dbManager.executeUpdate(insertQuery, schoolSubjString, conteString, questionString,
                    difficult, itemA, itemB, itemC, itemD);

            System.out.println("Linhas afetadas pela inserção: " + rowsAffected);

        } catch (SQLException ex) {
            System.out.println("Ocorreu um erro ao acessar a DB: " + ex.getMessage());
        }
    }

    public void dataUpload(String schoolSubjString, String conteString, String questionString, int difficult,
            String itemA, String itemB, String itemC) {
        try {
            DatabaseManager dbManager = new DatabaseManager("jdbc:mysql://localhost/TestQuestionDB", "root", "");

            String insertQuery = "INSERT INTO Questions (schoolSubject, content, question, difficult, itemA, itemB, itemC) VALUES (?, ?, ?, ?, ?, ?, ?)";

            int rowsAffected = dbManager.executeUpdate(insertQuery, schoolSubjString, conteString, questionString,
                    difficult, itemA, itemB, itemC);

            System.out.println("Linhas afetadas pela inserção: " + rowsAffected);

        } catch (SQLException ex) {
            System.out.println("Ocorreu um erro ao acessar a DB: " + ex.getMessage());
        }
    }

    public void dataUpload(String schoolSubjString, String conteString, String questionString, int difficult,
            String itemA, String itemB) {
        try {
            DatabaseManager dbManager = new DatabaseManager("jdbc:mysql://localhost/TestQuestionDB", "root", "");

            String insertQuery = "INSERT INTO Questions (schoolSubject, content, question, difficult, itemA, itemB) VALUES (?, ?, ?, ?, ?, ?)";

            int rowsAffected = dbManager.executeUpdate(insertQuery, schoolSubjString, conteString, questionString,
                    difficult, itemA, itemB);

            System.out.println("Linhas afetadas pela inserção: " + rowsAffected);

        } catch (SQLException ex) {
            System.out.println("Ocorreu um erro ao acessar a DB: " + ex.getMessage());
        }
    }

    public void dataDelete(int questionID) {

        try {
            DatabaseManager dbManager = new DatabaseManager("jdbc:mysql://localhost/TestQuestionDB", "root", "");

            String deleteQuery = String.format("DELETE FROM questions WHERE ID = ?");
            int rowsAffected = dbManager.executeUpdate(deleteQuery, questionID);

            System.out.println("Linhas afetadas pela ação: " + rowsAffected);

        } catch (SQLException ex) {
            System.out.println("Ocorreu um erro ao acessar a DB: " + ex.getMessage());
        }

    }

}

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
                int id = dados.getInt("id");
                String schoolSubject = dados.getString("schoolSubject");
                String content = dados.getString("content");
                String question = dados.getString("question");
                int difficult = dados.getInt("difficult");

                // Itens da questão
                ArrayList<String> items = new ArrayList<>();
                items.add(dados.getString("itemA"));
                items.add(dados.getString("itemB"));
                items.add(dados.getString("itemC"));
                items.add(dados.getString("itemD"));
                items.add(dados.getString("itemE"));
                items.add(dados.getString("itemF"));

                Question questao = new Question(id, schoolSubject, content, question, difficult, items);
                myQuestions.add(questao);
            }

        } catch (SQLException ex) {
            System.out.println("Ocorreu um erro ao acessar a DB: " + ex.getMessage());
        }

        return myQuestions;
    }

    public void dataUpload(Question question) {
        try {
            DatabaseManager dbManager = new DatabaseManager("jdbc:mysql://localhost/TestQuestionDB", "root", "");

            String insertQuery = "INSERT INTO Questions (schoolSubject, content, question, difficult, itemA, itemB, itemC, itemD, itemE, itemF) " +
                                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            int rowsAffected = dbManager.executeUpdate(insertQuery,
                question.getSchoolSubject(),
                question.getContent(),
                question.getQuestion(),
                question.getDifficult(),
                question.getItems().get(0),
                question.getItems().get(1),
                question.getItems().get(2),
                question.getItems().get(3),
                question.getItems().get(4),
                question.getItems().get(5));

            System.out.println("Linhas afetadas pela inserção: " + rowsAffected);

        } catch (SQLException ex) {
            System.out.println("Ocorreu um erro ao acessar a DB: " + ex.getMessage());
        }
    }

    public void dataDelete(int questionID) {
        try {
            DatabaseManager dbManager = new DatabaseManager("jdbc:mysql://localhost/TestQuestionDB", "root", "");

            String deleteQuery = "DELETE FROM questions WHERE ID = ?";
            int rowsAffected = dbManager.executeUpdate(deleteQuery, questionID);

            System.out.println("Linhas afetadas pela ação: " + rowsAffected);

        } catch (SQLException ex) {
            System.out.println("Ocorreu um erro ao acessar a DB: " + ex.getMessage());
        }
    }
}

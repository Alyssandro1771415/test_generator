package com.mycompany.mavenproject1.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.mycompany.mavenproject1.Model.Question;

public class QueryExecutions {

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
    
            ArrayList<String> items = new ArrayList<>();
    
            for (int i = 0; i < 6; i++) {
                if (i < question.getItems().size()) {
                    items.add(question.getItems().get(i));
                } else {
                    items.add("");
                }
            }
    
            int rowsAffected = dbManager.executeUpdate(insertQuery,
                question.getSchoolSubject(),
                question.getContent(),
                question.getQuestion(),
                question.getDifficult(),
                items.get(0),
                items.get(1),
                items.get(2),
                items.get(3),
                items.get(4),
                items.get(5));
        
        } catch (SQLException ex) {
            System.out.println("Ocorreu um erro ao acessar a DB: " + ex.getMessage());
        }
    }

    public void dataDelete(int questionID) {
        try {
            DatabaseManager dbManager = new DatabaseManager("jdbc:mysql://localhost/TestQuestionDB", "root", "");

            String deleteQuery = "DELETE FROM questions WHERE ID = ?";
            int rowsAffected = dbManager.executeUpdate(deleteQuery, questionID);


        } catch (SQLException ex) {
            System.out.println("Ocorreu um erro ao acessar a DB: " + ex.getMessage());
        }
    }

}

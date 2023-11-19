package com.mycompany.mavenproject1.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.mycompany.mavenproject1.Model.Question;

public class QueryExecutions {

    // Method to realize a general consult on the database and rreturn a ArrayList with de datas
    public ArrayList<Question> realizeConsult() {
        
        ArrayList<Question> myQuestions = new ArrayList<Question>();

        try {
            //Starting the database connection
            DatabaseManager dbManager = new DatabaseManager("jdbc:mysql://localhost/TestQuestionDB", "root", "");
            // Getting the datas of the database
            ResultSet dados = dbManager.executeQuery("SELECT * FROM questions");

            // Using the consult result to insert the datas in the model of Quesion class and insert in a ArrayList to return
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

    // Method to execute a datas upload in the database
    public void dataUpload(Question question) {
        try {
            // Starting the conection with the databse
            DatabaseManager dbManager = new DatabaseManager("jdbc:mysql://localhost/TestQuestionDB", "root", "");
    
            // Base of the query
            String insertQuery = "INSERT INTO Questions (schoolSubject, content, question, difficult, itemA, itemB, itemC, itemD, itemE, itemF) " +
                                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    
            ArrayList<String> items = new ArrayList<>();
    
            // Getting the datas to use on the query
            for (int i = 0; i < 6; i++) {
                if (i < question.getItems().size()) {
                    items.add(question.getItems().get(i));
                } else {
                    items.add("");
                }
            }
    
            // Get data by data of the items and include on the query
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

    // Method to delete a data on the database
    public void dataDelete(int questionID) {
        try {
            // Starting the database connection
            DatabaseManager dbManager = new DatabaseManager("jdbc:mysql://localhost/TestQuestionDB", "root", "");

            // Query of the data to delete
            String deleteQuery = "DELETE FROM questions WHERE ID = ?";
            // Executing the query
            int rowsAffected = dbManager.executeUpdate(deleteQuery, questionID);


        } catch (SQLException ex) {
            System.out.println("Ocorreu um erro ao acessar a DB: " + ex.getMessage());
        }
    }

}

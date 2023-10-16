package com.mycompany.mavenproject1;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class queryExecutions {
    
    public ArrayList<Question> realizeConsult(String consulta){

            ArrayList<Question> myQuestions = new ArrayList<Question>();
        
            try {
                DatabaseManager dbManager = new DatabaseManager("jdbc:mysql://localhost/TestQuestionDB", "root", "");
                ResultSet dados = dbManager.executeQuery(consulta);

                while (dados.next()) {
                    Question questao = new Question(dados.getInt("id"), dados.getString("schoolSubject"), dados.getString("content"), dados.getString("question"), dados.getInt("difficult"));
                    myQuestions.add(questao);
                }

            } catch (SQLException ex) {
                System.out.println("Ocorreu um erro ao acessar a DB: " + ex.getMessage());
            }

            return myQuestions;

    }

    public void dataUpload(String schoolSubjString, String conteString, String questionString, int difficult){
        try {
            DatabaseManager dbManager = new DatabaseManager("jdbc:mysql://localhost/TestQuestionDB", "root", "");

            String insertQuery = String.format("INSERT INTO Questions (schoolSubject, content, question, difficult) VALUES (?, ?, ?, ?)");
            int rowsAffected = dbManager.executeUpdate(insertQuery, schoolSubjString, conteString, questionString, difficult);

            System.out.println("Linhas afetadas pela inserção: " + rowsAffected);

        } catch (SQLException ex) {
            System.out.println("Ocorreu um erro ao acessar a DB: " + ex.getMessage());
        }
    }

}

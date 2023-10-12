package com.mycompany.mavenproject1;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class Mavenproject1 {

    public static void main(String[] args) throws SQLException {

        ArrayList<Question> meuArrayList = new ArrayList<Question>();
        
        try {
            DatabaseManager dbManager = new DatabaseManager("jdbc:mysql://localhost/TestQuestionDB", "root", "");
            ResultSet dados = dbManager.executeQuery("SELECT * FROM Questions");

            while (dados.next()) {
                Question questao = new Question(dados.getInt("id"), dados.getString("schoolSubject"), dados.getString("content"), dados.getString("question"), dados.getInt("difficult"));
                meuArrayList.add(questao);
            }

        } catch (SQLException ex) {
            System.out.println("Ocorreu um erro ao acessar a DB: " + ex.getMessage());
        }

        for(Question questao : meuArrayList){
            System.out.println(questao);
        }
        
    }
}

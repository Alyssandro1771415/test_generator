package com.mycompany.mavenproject1;
import java.sql.ResultSet;
import java.sql.SQLException;


public class Mavenproject1 {

    public static void main(String[] args) throws SQLException {
        
        try {
            DatabaseManager dbManager = new DatabaseManager("jdbc:mysql://localhost/TestQuestionDB", "root", "");
            ResultSet dados = dbManager.executeQuery("SELECT * FROM Questions");

            while (dados.next()) {
                System.out.println("ID: " + dados.getString("id"));
            }

        } catch (SQLException ex) {
            System.out.println("Ocorreu um erro ao acessar a DB: " + ex.getMessage());
        }
        
    }
}

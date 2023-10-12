package com.mycompany.mavenproject1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Mavenproject1 {

    public static void main(String[] args) throws SQLException {
        
        Connection conexao = null;
                
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conexao = DriverManager.getConnection("jdbc:mysql://localhost/TestQuestionDB", "root", "");
            ResultSet dados = conexao.createStatement().executeQuery("select * from questions");
            
            while(dados.next()){
                System.out.print("id: " + dados.getString("id") + "\n");
            }
            
        } catch (ClassNotFoundException ex) {

            Logger.getLogger(Mavenproject1.class.getName()).log(Level.SEVERE, null, ex);
        
        } catch (SQLException ex) {

                System.out.println("Ocorreu um erro ao acessar a DB");

        } finally {
            if(conexao != null){
                conexao.close();
            }
        }
        
        
    }
}

package com.mycompany.mavenproject1;

import java.sql.SQLException;
import java.util.ArrayList;

public class Mavenproject1 {

    public static void main(String[] args) throws SQLException {
        
        queryExecutions consultor = new queryExecutions();

        consultor.dataUpload("Português", "Tempos Verbais", "Qual o tempo verbal da frase: 'Eu vejo o futuro repetir o passado'", 1);

        ArrayList<Question> lista = consultor.realizeConsult("SELECT * FROM questions");

        for(Question i : lista){
            System.out.println(i);
        }

    }
}

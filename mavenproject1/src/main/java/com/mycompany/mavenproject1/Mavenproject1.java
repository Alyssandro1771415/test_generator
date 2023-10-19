package com.mycompany.mavenproject1;

import java.sql.SQLException;
import java.util.ArrayList;

public class Mavenproject1 {

    public static void main(String[] args) throws SQLException {
        
        /*queryExecutions consultor = new queryExecutions();

        consultor.dataUpload("Português", "Tempos Verbais", "Qual o tempo verbal da frase: 'Eu vejo o futuro repetir o passado'", 1, "Passado", "Futuro", "Presente");

        ArrayList<Question> lista = consultor.realizeConsult();

        for(Question i : lista){
            System.out.println(i.getQuestion());
        }

        consultor.dataDelete(11);*/

        ArrayList<Question> ListaQuestoes = new ArrayList<>();
        ListaQuestoes.add(new Question(1, "Matemática", "Derivadas","Resolva a equação f'(x) = 2x + 1 = 0.", 1));
        ListaQuestoes.add(new Question(4, "Matemática", "Soma de Frações" ,"Resolva a equação 2/5 + 7/8.", 2));
        ListaQuestoes.add(new Question(7, "Matemática", "Derivadas" ,"f'(x) = x^2 + 7x + 5.", 3));
        ListaQuestoes.add(new Question(9, "Matemática", "Operações com conjuntos","Resolva a equação VUW.", 2));

        Test geradorTest = new Test();
        geradorTest.gerarArquivo(ListaQuestoes);

    }
}

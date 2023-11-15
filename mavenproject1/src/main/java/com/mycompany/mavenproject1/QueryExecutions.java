package com.mycompany.mavenproject1;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class QueryExecutions {

    // Método para realizar consulta no banco de dados e retornar uma lista de perguntas
    public ArrayList<Question> realizeConsult() {
        ArrayList<Question> myQuestions = new ArrayList<Question>();

        try {
            // Conectar ao banco de dados
            DatabaseManager dbManager = new DatabaseManager("jdbc:mysql://localhost/TestQuestionDB", "root", "");
            // Executar a consulta SQL para obter todas as perguntas
            ResultSet dados = dbManager.executeQuery("SELECT * FROM questions");

            // Iterar sobre os resultados da consulta
            while (dados.next()) {
                // Extrair os dados da pergunta do resultado da consulta
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

                // Criar objeto Question e adicioná-lo à lista
                Question questao = new Question(id, schoolSubject, content, question, difficult, items);
                myQuestions.add(questao);
            }

        } catch (SQLException ex) {
            // Tratar exceção em caso de erro ao acessar o banco de dados
            System.out.println("Ocorreu um erro ao acessar a DB: " + ex.getMessage());
        }

        // Retornar a lista de perguntas
        return myQuestions;
    }

    // Método para enviar uma nova pergunta para o banco de dados
    public void dataUpload(Question question) {
        try {
            // Conectar ao banco de dados
            DatabaseManager dbManager = new DatabaseManager("jdbc:mysql://localhost/TestQuestionDB", "root", "");

            // Construir a consulta SQL para inserir uma nova pergunta
            String insertQuery = "INSERT INTO Questions (schoolSubject, content, question, difficult, itemA, itemB, itemC, itemD, itemE, itemF) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            // Criar lista de itens da pergunta
            ArrayList<String> items = new ArrayList<>();

            // Preencher a lista de itens da pergunta, adicionando valores vazios se necessário
            for (int i = 0; i < 6; i++) {
                if (i < question.getItems().size()) {
                    items.add(question.getItems().get(i));
                } else {
                    items.add("");
                }
            }

            // Executar a atualização no banco de dados para inserir a nova pergunta
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
            // Tratar exceção em caso de erro ao acessar o banco de dados
            System.out.println("Ocorreu um erro ao acessar a DB: " + ex.getMessage());
        }
    }

    // Método para excluir uma pergunta do banco de dados com base no ID
    public void dataDelete(int questionID) {
        try {
            // Conectar ao banco de dados
            DatabaseManager dbManager = new DatabaseManager("jdbc:mysql://localhost/TestQuestionDB", "root", "");

            // Construir a consulta SQL para excluir a pergunta com base no ID
            String deleteQuery = "DELETE FROM questions WHERE ID = ?";
            // Executar a atualização no banco de dados para excluir a pergunta
            int rowsAffected = dbManager.executeUpdate(deleteQuery, questionID);

        } catch (SQLException ex) {
            // Tratar exceção em caso de erro ao acessar o banco de dados
            System.out.println("Ocorreu um erro ao acessar a DB: " + ex.getMessage());
        }
    }
}

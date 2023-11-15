package com.mycompany.mavenproject1;

import java.util.ArrayList;

public class Question {

    // Atributos da classe Question
    private int id; // Identificador único da pergunta
    private String schoolSubject; // Disciplina da pergunta
    private String content; // Subconteúdo associado à pergunta
    private String question; // Descrição da pergunta
    private int difficult; // Nível de dificuldade da pergunta
    private ArrayList<String> items = new ArrayList<>(); // Lista de itens associados à pergunta

    // Construtores da classe Question
    public Question() {
        // Construtor vazio
    }

    public Question(int id, String schoolSubject, String content, String question, int difficult, ArrayList<String> items) {
        // Construtor com parâmetros para inicializar todos os atributos
        this.id = id;
        this.schoolSubject = schoolSubject;
        this.content = content;
        this.question = question;
        this.difficult = difficult;
        this.items = items;
    }

    public Question(String schoolSubject, String content, String question, int difficult, ArrayList<String> items) {
        // Construtor alternativo sem o ID, útil para criar novas perguntas sem um ID pré-determinado
        this.schoolSubject = schoolSubject;
        this.content = content;
        this.question = question;
        this.difficult = difficult;
        this.items = items;
    }

    // Métodos de acesso (getters) para obter os valores dos atributos
    public int getId() {
        return id;
    }

    public String getSchoolSubject() {
        return schoolSubject;
    }

    public String getContent() {
        return content;
    }

    public String getQuestion() {
        return question;
    }

    public int getDifficult() {
        return difficult;
    }

    public ArrayList<String> getItems() {
        return items;
    }

    public String getSpecificItem(int itemIndex) {
        return items.get(itemIndex);
    }

    // Métodos de modificação (setters) para alterar os valores dos atributos
    public void setId(int id) {
        this.id = id;
    }

    public void setSchoolSubject(String schoolSubject) {
        this.schoolSubject = schoolSubject;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public void setDifficult(int difficult) {
        this.difficult = difficult;
    }

    public void setItems(ArrayList<String> items) {
        this.items = items;
    }

    public void setEspecificItem(String item) {
        items.add(item);
    }

    // Método para verificar se a pergunta é de múltipla escolha com base nos itens associados
    public boolean isA_Multiplechoice(ArrayList<String> arrayItems) {
        if (!(arrayItems.isEmpty())) {
            return true;
        }
        return false;
    }

    // Método para contar o número de itens não vazios associados à pergunta
    public int countItems(ArrayList<String> items) {
        int count = 0;
        for (String item : items) {
            if (!item.isEmpty()) {
                count++;
            }
        }
        return count;
    }

    // Sobrescrita do método toString para fornecer uma representação de string formatada da pergunta
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("ID: ").append(id).append("\n");
        sb.append("Disciplina: ").append(schoolSubject).append("\n");
        sb.append("Subconteúdo: ").append(content).append("\n");
        sb.append("Descrição da Questão: ").append(question).append("\n");
        sb.append("Nível de Dificuldade: ").append(difficult).append("\n");
        sb.append("Itens da Questão:\n");
        for (int i = 0; i < items.size(); i++) {
            sb.append("Item ").append(i + 1).append(": ").append(items.get(i)).append("\n");
        }
        return sb.toString();
    }
}

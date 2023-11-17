package com.mycompany.mavenproject1.Model;

import java.util.ArrayList;

public class Question {

    private int id;//ID
    private String schoolSubject; //Disciplina
    private String content; //Subconteúdo
    private String question; // Descrição
    private int difficult;
    private ArrayList<String> items = new ArrayList<>();

    public Question(){

    }

    public Question(int id, String schoolSubject, String content, String question, int difficult, ArrayList<String> items) {
        this.id = id;
        this.schoolSubject = schoolSubject;
        this.content = content;
        this.question = question;
        this.difficult = difficult;
        this.items = items;
    }

    public Question(String schoolSubject, String content, String question, int difficult, ArrayList<String> items) {
        this.schoolSubject = schoolSubject;
        this.content = content;
        this.question = question;
        this.difficult = difficult;
        this.items = items;
    }

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

    public ArrayList<String> getItems(){
        return items;
    }

    public String getSpecificItem(int itemIndex) {
        return items.get(itemIndex);
    }

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

    public boolean isA_Multiplechoice(ArrayList<String> arrayItems) {
        if (!(arrayItems.isEmpty())) {
            return true;
        }
        return false;
    }

    public int countItems(ArrayList<String> items) {
        int count = 0;
        for (String item : items) {
            if (!item.isEmpty()) {
                count++;
            }
        }
        return count;
    }
    
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
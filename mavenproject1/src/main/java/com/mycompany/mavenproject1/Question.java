package com.mycompany.mavenproject1;

import java.util.ArrayList;

public class Question {

    private int id;//ID
    private String schoolSubject; //Disciplina
    private String content; //Subconteúdo
    private String question; // Descrição
    private int difficult;
    private ArrayList<String> items = new ArrayList<>();

    public Question(int id, String schoolSubject, String content, String question, int difficult, ArrayList<String> items) {
        this.id = id;
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

}
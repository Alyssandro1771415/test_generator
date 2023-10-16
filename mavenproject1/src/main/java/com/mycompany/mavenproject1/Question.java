package com.mycompany.mavenproject1;

public class Question {
    
    private int id;
    private String schoolSubject; 
    private String content; 
    private String question;
    private int difficult;

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

    public Question(int id, String schoolSubject, String content, String question, int difficult) {
        this.id = id;
        this.schoolSubject = schoolSubject;
        this.content = content;
        this.question = question;
        this.difficult = difficult;
    }

    @Override
    public String toString() {
        return "Question{" +
                "id=" + id +
                ", schoolSubject='" + schoolSubject + '\'' +
                ", content='" + content + '\'' +
                ", question='" + question + '\'' +
                ", difficult=" + difficult +
                '}';
    }

}

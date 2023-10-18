package com.mycompany.mavenproject1;

import java.util.ArrayList;

public interface testCreator {

    /* Aqui será para gerar aleatoriamente */
    ArrayList<Question> getQuestionsRandom(int quantEasyQuest, int quantyModerQuest, int quantHardQuest, ArrayList<Question> questions);
    
    /* Aqui ele apenas irá reordenar as questões escolhidas pelo professor */
    ArrayList<Question> getQuestionsRandom(ArrayList<Question> questionsFromDB);
    /*Elas não retorna nada pq essa função já vai gerar e 
    baixar na máquina do professor a prova criada*/

    void generateTest(ArrayList<Question> questions);

}

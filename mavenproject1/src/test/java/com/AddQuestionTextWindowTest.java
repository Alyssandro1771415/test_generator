package com;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.mycompany.mavenproject1.Model.Question;
import com.mycompany.mavenproject1.View.AddQuestionTextWindow;
import com.mycompany.mavenproject1.View.ItemsSelectorWindow;
import com.mycompany.mavenproject1.View.QuestionViewWindow;

public class AddQuestionTextWindowTest {

    private AddQuestionTextWindow addQuestionTextWindow;

    @Before
    public void setUp() throws Exception {
        // Crie os objetos necessários antes de cada teste, se necessário
        Question question = new Question(/* parâmetros da pergunta */);
        addQuestionTextWindow = new AddQuestionTextWindow(AddQuestionTextWindow.OPENED, question);
    }

    @After
    public void tearDown() throws Exception {
        // Execute ações de limpeza após cada teste, se necessário
        addQuestionTextWindow = null;
    }

    @Test
    public void testSetQuestionEntries() {
        addQuestionTextWindow.textAreaQuestionDescription.setText("Exemplo de texto da pergunta");

        addQuestionTextWindow.setQuestionEntries();

        assertEquals("Exemplo de texto da pergunta", addQuestionTextWindow.question.getQuestion());
    }

}

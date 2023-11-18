package com;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import java.util.ArrayList;
import org.junit.Before;
import org.junit.Test;

import com.mycompany.mavenproject1.Model.Question;

public class QuestionTeste {

    private Question question;

    @Before
    public void setUp() {
        // Configurar dados de teste, se necessário
        question = new Question();
    }

    @Test
    public void testGettersAndSetters() {
        // Testar os métodos getters e setters
        question.setId(1);
        assertEquals(1, question.getId());

        question.setSchoolSubject("Math");
        assertEquals("Math", question.getSchoolSubject());

        question.setContent("Algebra");
        assertEquals("Algebra", question.getContent());

        question.setQuestion("Solve the equation");
        assertEquals("Solve the equation", question.getQuestion());

        question.setDifficult(3);
        assertEquals(3, question.getDifficult());

        ArrayList<String> items = new ArrayList<>();
        items.add("Option A");
        items.add("Option B");
        question.setItems(items);
        assertEquals(items, question.getItems());
    }

    @Test
    public void testGetSpecificItem() {
        // Testar o método getSpecificItem
        ArrayList<String> items = new ArrayList<>();
        items.add("Option A");
        items.add("Option B");
        question.setItems(items);

        assertEquals("Option A", question.getSpecificItem(0));
        assertEquals("Option B", question.getSpecificItem(1));
    }

    @Test
    public void testSetEspecificItem() {
        // Testar o método setEspecificItem
        question.setEspecificItem("Option A");
        question.setEspecificItem("Option B");

        ArrayList<String> items = new ArrayList<>();
        items.add("Option A");
        items.add("Option B");

        assertEquals(items, question.getItems());
    }

    @Test
    public void testIsA_Multiplechoice() {
        // Testar o método isA_Multiplechoice
        ArrayList<String> items = new ArrayList<>();
        items.add("Option A");
        items.add("Option B");
        question.setItems(items);

        assertTrue(question.isA_Multiplechoice(items));

        ArrayList<String> emptyItems = new ArrayList<>();
        assertFalse(question.isA_Multiplechoice(emptyItems));
    }

    @Test
    public void testCountItems() {
        // Testar o método countItems
        ArrayList<String> items = new ArrayList<>();
        items.add("Option A");
        items.add("Option B");
        items.add("");
        question.setItems(items);

        assertEquals(2, question.countItems(items));
    }

    @Test
    public void testToString() {
        // Testar o método toString
        question.setId(1);
        question.setSchoolSubject("Math");
        question.setContent("Algebra");
        question.setQuestion("Solve the equation");
        question.setDifficult(3);
        ArrayList<String> items = new ArrayList<>();
        items.add("Option A");
        items.add("Option B");
        question.setItems(items);

        String expectedString = "ID: 1\n" +
                "Disciplina: Math\n" +
                "Subconteúdo: Algebra\n" +
                "Descrição da Questão: Solve the equation\n" +
                "Nível de Dificuldade: 3\n" +
                "Itens da Questão:\n" +
                "Item 1: Option A\n" +
                "Item 2: Option B\n";

        assertEquals(expectedString, question.toString());
    }
}
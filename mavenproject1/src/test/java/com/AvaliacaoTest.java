package com;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import org.junit.Before;
import org.junit.Test;

import com.mycompany.mavenproject1.Model.Question;
import com.mycompany.mavenproject1.Model.Avaliacao;

public class AvaliacaoTest {

    @Test
    public void testAvaliacaoConstructorGettersAndSetters() {
        // Dados de exemplo para o teste
        String institution = "Escola Exemplo";
        String schoolSubject = "Matemática";
        String educatorName = "Professor Teste";
        int testsNumber = 1;
        ArrayList<Question> questionsList = new ArrayList<>();

        // Criar uma instância da classe Avaliacao usando o construtor
        Avaliacao avaliacao = new Avaliacao(institution, schoolSubject, educatorName, testsNumber, questionsList);

        // Verificar se os valores foram atribuídos corretamente usando os getters
        assertNotNull(avaliacao);
        assertEquals(institution, avaliacao.getInstitution());
        assertEquals(schoolSubject, avaliacao.getSchoolSubject());
        assertEquals(educatorName, avaliacao.getEducatorName());
        assertEquals(testsNumber, avaliacao.getTestsNumber());
        assertEquals(questionsList, avaliacao.getQuestionsList());

        questionsList.add(new Question("Matemática", "Raízes", "Qual a raiz de Pi?", 1, new ArrayList<>()));

        // Reatribuindo valores para testar os setters
        avaliacao.setInstitution("Escola Setted");
        avaliacao.setSchoolSubject("História");
        avaliacao.setEducatorName("Novo Professor");
        avaliacao.setTestsNumber(1);
        avaliacao.setQuestionsList(questionsList);

        // Verificar se os valores foram atribuídos corretamente usando os getters
        assertNotNull(avaliacao);
        assertEquals("Escola Setted", avaliacao.getInstitution());
        assertEquals("História", avaliacao.getSchoolSubject());
        assertEquals("Novo Professor", avaliacao.getEducatorName());
        assertEquals(testsNumber, avaliacao.getTestsNumber());
        assertEquals(questionsList, avaliacao.getQuestionsList());
    }

    @Test
    public void testToStringMethod() {
        // Dados de exemplo para o teste
        String institution = "Escola Exemplo";
        String schoolSubject = "Matemática";
        String educatorName = "Professor Teste";
        int testsNumber = 1;
        ArrayList<Question> questionsList = new ArrayList<>();
        // Adicione algumas questões à lista
        questionsList.add(new Question());
        questionsList.add(new Question());

        // Criar uma instância da classe Avaliacao usando o construtor
        Avaliacao avaliacao = new Avaliacao(institution, schoolSubject, educatorName, testsNumber, questionsList);

        // Verificar se o método toString gera a saída esperada
        String expectedToString = "Instituição: " + institution + "\n" +
                "Disciplina: " + schoolSubject + "\n" +
                "Professor: " + educatorName + "\n" +
                "---------------------------------------------------------------\n" +
                "\n1) ID: 0" +
                "\nDisciplina: null" +
                "\nSubconteúdo: null" +
                "\nDescrição da Questão: null" +
                "\nNível de Dificuldade: 0" +
                "\nItens da Questão:" +
                "\n\n2) ID: 0" +
                "\nDisciplina: null" +
                "\nSubconteúdo: null" +
                "\nDescrição da Questão: null" +
                "\nNível de Dificuldade: 0" +
                "\nItens da Questão:\n";
        assertEquals(expectedToString, avaliacao.toString());
    }

    @Test
    public void testRemoveOpenedQuestions() {
        ArrayList<Question> questions = new ArrayList<>();

        questions.add(new Question("História", "II Guerra Mundial", "Quem foi Adolf Hitler?", 1,
                new ArrayList<>(List.of("Opção 1", "Opção 2", "Opção 3"))));
        questions.add(new Question("Geografia", "Rio Amazonas", "Qual é o rio mais longo do mundo?", 2,
                new ArrayList<>(List.of("Rio Amazonas", "Rio Nilo", "Rio Yangtzé"))));
        questions.add(new Question("Ciências", "Ácido clorídrico",
                "Qual é a fórmula química do ácido clorídrico?", 3,
                new ArrayList<>(List.of("HCl", "H2SO4", "NaOH"))));
        questions.add(new Question("Literatura", "Dom Quixote", "Quem é o autor de 'Dom Quixote'?", 1,
                new ArrayList<>(List.of("", "", ""))));

        Avaliacao avaliacao = new Avaliacao("Escola Exemplo", "Matemática", "Professor Teste", 1, questions);

        ArrayList<Question> result = avaliacao.removeOpenedQuestions(questions);

        assertEquals(3, result.size());
        assertEquals("Quem foi Adolf Hitler?", result.get(0).getQuestion());
        assertEquals("Qual é o rio mais longo do mundo?", result.get(1).getQuestion());
        assertEquals("Qual é a fórmula química do ácido clorídrico?", result.get(2).getQuestion());
    }

    

}

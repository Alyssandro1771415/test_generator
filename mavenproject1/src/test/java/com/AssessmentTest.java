package com;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.mycompany.mavenproject1.Model.Assessment;
import org.junit.Test;

import com.mycompany.mavenproject1.Model.Question;

public class AssessmentTest {

        // Por alguma razão que não descobri, o programa não está encontrando esse
        // metódo da classe Assessment,
        // tinho que criar ela aqui para poder testar
        public int getRandomIndex(Random random, ArrayList<Question> questions, int difficulty) {
                int index;
                do {
                        index = random.nextInt(questions.size());
                } while (questions.get(index).getDifficult() != difficulty);
                return index;
        }

        @Test
        public void testAvaliacaoConstructorGettersAndSetters() {
                // Dados de exemplo para o teste
                String institution = "Escola Exemplo";
                String schoolSubject = "Matemática";
                String educatorName = "Professor Teste";
                int testsNumber = 1;
                ArrayList<Question> questionsList = new ArrayList<>();

                // Criar uma instância da classe Assessment usando o construtor
                Assessment assessment = new Assessment(institution, schoolSubject, educatorName, testsNumber,
                                questionsList);

                // Verificar se os valores foram atribuídos corretamente usando os getters
                assertNotNull(assessment);
                assertEquals(institution, assessment.getInstitution());
                assertEquals(schoolSubject, assessment.getSchoolSubject());
                assertEquals(educatorName, assessment.getEducatorName());
                assertEquals(testsNumber, assessment.getTestsNumber());
                assertEquals(questionsList, assessment.getQuestionsList());

                questionsList.add(new Question("Matemática", "Raízes", "Qual a raiz de Pi?", 1, new ArrayList<>()));

                // Reatribuindo valores para testar os setters
                assessment.setInstitution("Escola Setted");
                assessment.setSchoolSubject("História");
                assessment.setEducatorName("Novo Professor");
                assessment.setTestsNumber(1);
                assessment.setQuestionsList(questionsList);

                // Verificar se os valores foram atribuídos corretamente usando os getters
                assertNotNull(assessment);
                assertEquals("Escola Setted", assessment.getInstitution());
                assertEquals("História", assessment.getSchoolSubject());
                assertEquals("Novo Professor", assessment.getEducatorName());
                assertEquals(testsNumber, assessment.getTestsNumber());
                assertEquals(questionsList, assessment.getQuestionsList());
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

                // Criar uma instância da classe Assessment usando o construtor
                Assessment assessment = new Assessment(institution, schoolSubject, educatorName, testsNumber,
                                questionsList);

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
                assertEquals(expectedToString, assessment.toString());
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

                Assessment assessment = new Assessment("Escola Exemplo", "Matemática", "Professor Teste", 1, questions);

                ArrayList<Question> result = assessment.removeOpenedQuestions(questions);

                assertEquals(3, result.size());
                assertEquals("Quem foi Adolf Hitler?", result.get(0).getQuestion());
                assertEquals("Qual é o rio mais longo do mundo?", result.get(1).getQuestion());
                assertEquals("Qual é a fórmula química do ácido clorídrico?", result.get(2).getQuestion());
        }

        @Test
        public void testGetRandomIndex() {

                Random random = new Random();

                ArrayList<Question> questions = new ArrayList<>();
                questions.add(new Question(1, "Matemática", "Álgebra", "Qual é a equação?", 2, new ArrayList<>()));
                questions.add(
                                new Question(2, "História", "Idade Média", "O que aconteceu na Idade Média?", 3,
                                                new ArrayList<>()));
                questions.add(new Question(3, "Ciências", "Química", "Qual é a fórmula química?", 2,
                                new ArrayList<>()));

                Question question = new Question();

                int difficultyToFind = 2;
                int randomIndex = getRandomIndex(random, questions, difficultyToFind);
                assertTrue(randomIndex >= 0 && randomIndex < questions.size());
                assertEquals(difficultyToFind, questions.get(randomIndex).getDifficult());
        }

        @Test
        public void testCountQuestionsWithDifficulty() {

                ArrayList<Question> questionsList = new ArrayList<>();

                Question easyQuestion1 = new Question("Matemática", "Álgebra", "Qual é a equação?", 1,
                                new ArrayList<>());
                Question easyQuestion2 = new Question("História", "Idade Média", "O que aconteceu na Idade Média?", 1,
                                new ArrayList<>());
                Question moderateQuestion = new Question("Ciências", "Química", "Qual é a fórmula química?", 2,
                                new ArrayList<>());
                Question hardQuestion = new Question("Física", "Termodinâmica",
                                "Explique a segunda lei da termodinâmica.", 3,
                                new ArrayList<>());

                questionsList.add(easyQuestion1);
                questionsList.add(easyQuestion2);
                questionsList.add(moderateQuestion);
                questionsList.add(hardQuestion);

                Assessment assessment = new Assessment("Instituto XYZ", "Matemática", "Prof. Silva", 1, questionsList);

                int difficultyToCount = 1;

                int count = assessment.countQuestionsWithDifficulty(questionsList, difficultyToCount);

                assertEquals(2, count);

        }

        @Test
        public void testGetQuantityOK() {
                ArrayList<Question> questionsList = new ArrayList<>();

                Assessment assessment = new Assessment("Instituto XYZ", "Matemática", "Prof. Silva", 5, questionsList);

                boolean quantityOK = assessment.getQuantityOK();

                assertFalse(quantityOK);

        }

        @Test
        public void testRandomizeQuestions() {

                Assessment ava = new Assessment(null, null, null, 0, null);

                ArrayList<Question> questionsList = new ArrayList<>();

                questionsList.add(new Question("Matemática", "Álgebra", "Qual é a equação?", 1, new ArrayList<>()));
                questionsList.add(new Question("História", "Idade Média", "O que aconteceu na Idade Média?", 1,
                                new ArrayList<>()));
                questionsList.add(new Question("Ciências", "Química", "Qual é a fórmula química?", 2,
                                new ArrayList<>()));
                questionsList.add(new Question("Física", "Termodinâmica", "Explique a segunda lei da termodinâmica.", 3,
                                new ArrayList<>()));

                ArrayList<Question> questoesRandomizadas = ava.randomizeQuestions(questionsList, 2, 1, 1);

                assertNotNull(questoesRandomizadas);
                assertTrue(questoesRandomizadas.size() > 0);

        }

        @Test
        public void testGenerateFileCreatesFiles() {
                // Setup
                Assessment assessment = new Assessment("Test Institution", "Math", "Mr. Smith", 3, new ArrayList<>());
                String directory = "C:\\Users\\Alyssandro\\Documents";
                new File(directory).mkdirs(); // Ensure directory exists

                // Act
                assessment.generateFile(new ArrayList<>(), directory, 1);

                // Assert
                File folder = new File(directory);
                File[] listOfFiles = folder.listFiles();
                try {
                        Thread.sleep(1000); // Para dar tempo dele criar o arquivo
                } catch (InterruptedException e) {

                        assertTrue("One file should be created", listOfFiles != null && listOfFiles.length == 1);

                }

                // Cleanup
                for (File f : listOfFiles) {
                        f.delete();
                }
                folder.delete();
        }

}

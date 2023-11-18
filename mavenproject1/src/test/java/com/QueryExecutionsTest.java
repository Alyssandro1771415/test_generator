package com;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;

import com.mycompany.mavenproject1.Model.Question;
import com.mycompany.mavenproject1.Repository.QueryExecutions;

public class QueryExecutionsTest {

    private QueryExecutions queryExecutions;

    @Before
    public void setUp() {

        queryExecutions = new QueryExecutions();

    }

    @Test
    public void testRealizeConsult() {
        
        ArrayList<Question> resultQuestions = queryExecutions.realizeConsult();

        assertNotNull(resultQuestions); 
    }

    @Test
    public void testDataUpload() {

        Question testQuestion = new Question("Matemática", "Álgebra", "Qual é a equação?", 2, new ArrayList<>());

        queryExecutions.dataUpload(testQuestion);

    }

    @Test
    public void testDataDelete() {

        int testQuestionID = 62; 

        queryExecutions.dataDelete(testQuestionID);

    }

}

package com.mycompany.mavenproject1;

import java.sql.SQLException;

public class Mavenproject1 {

    public static void main(String[] args) throws SQLException {
        
        queryExecutions consultor = new queryExecutions();

        consultor.realizeConsult();

    }
}
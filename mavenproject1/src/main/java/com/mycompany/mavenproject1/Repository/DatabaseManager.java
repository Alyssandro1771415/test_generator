package com.mycompany.mavenproject1.Repository;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DatabaseManager {

    private Connection connection;

    public DatabaseManager(String url, String user, String password) throws SQLException {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(url, user, password);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DatabaseManager.class.getName()).log(Level.SEVERE, null, ex);
            throw new SQLException("Driver do banco de dados não encontrado", ex);
        }
    }

    public ResultSet executeQuery(String query) throws SQLException {
        if (connection == null || connection.isClosed()) {
            throw new SQLException("Conexão com o banco de dados não está disponível");
        }

        return connection.createStatement().executeQuery(query);
    }

    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public int executeUpdate(String query, Object... params) throws SQLException {
        if (connection == null || connection.isClosed()) {
            throw new SQLException("Conexão com o banco de dados não está disponível");
        }

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            // Substitui os marcadores de posição na consulta pelos parâmetros fornecidos
            for (int i = 0; i < params.length; i++) {
                preparedStatement.setObject(i + 1, params[i]);
            }

            // Executa a atualização e retorna o número de linhas afetadas
            return preparedStatement.executeUpdate();
        }
    }
}

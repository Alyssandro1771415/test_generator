package com.mycompany.mavenproject1;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DatabaseManager {

    private Connection conexao;

    public DatabaseManager(String url, String user, String password) throws SQLException {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conexao = DriverManager.getConnection(url, user, password);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DatabaseManager.class.getName()).log(Level.SEVERE, null, ex);
            throw new SQLException("Driver do banco de dados não encontrado", ex);
        }
    }

    public ResultSet executeQuery(String query) throws SQLException {
        if (conexao == null || conexao.isClosed()) {
            throw new SQLException("Conexão com o banco de dados não está disponível");
        }

        return conexao.createStatement().executeQuery(query);
    }

    public void closeConnection() {
        try {
            if (conexao != null && !conexao.isClosed()) {
                conexao.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public int executeUpdate(String query, Object... params) throws SQLException {
        if (conexao == null || conexao.isClosed()) {
            throw new SQLException("Conexão com o banco de dados não está disponível");
        }

        try (PreparedStatement preparedStatement = conexao.prepareStatement(query)) {
            // Substitui os marcadores de posição na consulta pelos parâmetros fornecidos
            for (int i = 0; i < params.length; i++) {
                preparedStatement.setObject(i + 1, params[i]);
            }

            // Executa a atualização e retorna o número de linhas afetadas
            return preparedStatement.executeUpdate();
        }
    }
}
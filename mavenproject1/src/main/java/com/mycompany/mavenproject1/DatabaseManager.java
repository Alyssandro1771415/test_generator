package com.mycompany.mavenproject1;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DatabaseManager {

    private Connection connection;

    // Método para inicializar a conexão com o banco de dados SQL
    public DatabaseManager(String url, String user, String password) throws SQLException {
        try {
            // carregamento do driver JDBC do MySQL
            Class.forName("com.mysql.jdbc.Driver");
            // Armazenamento da conexão em uma variável
            connection = DriverManager.getConnection(url, user, password);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DatabaseManager.class.getName()).log(Level.SEVERE, null, ex);
            throw new SQLException("Driver do banco de dados não encontrado", ex);
        }
    }

    // Método para executar um comando/query SQL
    public ResultSet executeQuery(String query) throws SQLException {
        if (connection == null || connection.isClosed()) {
            throw new SQLException("Conexão com o banco de dados não está disponível");
        }

        return connection.createStatement().executeQuery(query);
    }

    // Método para fechar a conexão com o banco de dados após terem sidfo feitas as ações desejadas, isso melhora o desenpenho do programa já que ele não manterá a conexão sempre em aberta
    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // Execução de alterações no banco de dados
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

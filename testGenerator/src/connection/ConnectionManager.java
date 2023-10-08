package connection;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionManager {

    private static final String url = "jdbc:mysql://localhost:3306/TestQuestionDB";
    private static final String user = "root";
    private static final String password = "";

    private static Connection conn;

    public static Connection getConnection() {
        try {
            if (conn == null || conn.isClosed()) {
                conn = DriverManager.getConnection(url, user, password);
            }
            return conn;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao obter a conexão: " + e.getMessage(), e);
        }
    }
}

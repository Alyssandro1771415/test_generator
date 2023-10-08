import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import connection.ConnectionManager;

public class App {

    public static void main(String[] args) {
        try (Connection connection = ConnectionManager.getConnection()) {
            if (connection != null) {
                String sql = "SELECT * FROM Questions";

                try (PreparedStatement statement = connection.prepareStatement(sql);
                     ResultSet resultSet = statement.executeQuery()) {

                    while (resultSet.next()) {
                        String nome = resultSet.getString("nome");
                        System.out.println("Nome: " + nome);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("Falha na conexão.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

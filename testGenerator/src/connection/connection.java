package connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class connection {

    private static final String url = "jdbc:mysql://localhost:3306/TestQuestionDB";
    private static final String user = "root";
    private static final String password = "";

    private static Connection conn;

    public static Connection gConnection(){

        try {
            if(conn == null){
                conn = DriverManager.getConnection(url, user, password);
            }
            return conn; 
        } catch (Exception e) {
            e.printStackTrace();
            return null; 
        }
    }
}

package database;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
public class Database {
    private static String DB_user = "root";
    private static String DB_password = "";
    private static String connectionUrl;
    private static int port = 3306;
    private static String DB_name = "agencija";
    private static Connection connection;

    public static void init() { // main() -> init()
        try {
            connectionUrl = "jdbc:mysql://localhost" + ":" + port + "/" + DB_name;
            connection = DriverManager.getConnection(connectionUrl, DB_user, DB_password);
            System.out.println("Uspjesno ste se konektovali na bazu:" + connectionUrl);
            Read.connection = connection;
            Write.connection = connection;
            Read.read();
        } catch (SQLException e){
            e.printStackTrace();
        } //catch (ClassNotFoundException e) {
            //throw new RuntimeException(e);
        //}
    }
}

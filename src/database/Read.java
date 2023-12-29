package database;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Read {

    public static void read(Connection connection) throws SQLException{
        readAdmin(connection);
        readKlijent(connection);
        readBankovniRacun(connection);
        readAranzman(connection);
        readRezervacija(connection);
        readSmjestaj(connection);
    }

    public static void readAdmin(Connection connection) throws SQLException {

        String upit = "SELECT * FROM admin;";
        Statement iskaz = connection.createStatement();
        ResultSet rezultat = iskaz.executeQuery(upit);

    }

    public static void readKlijent(Connection connection){

    }

    public static void readBankovniRacun(Connection connection){

    }

    public static void readAranzman(Connection connection){

    }

    public static void readRezervacija(Connection connection){

    }

    public static void readSmjestaj(Connection connection){

    }
}

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class PostgresConnector {

    //Connection string
    public static String postgresURL = "jdbc:postgresql://localhost:5432/postgres?user=postgres&password=geheim";
    public static String user = "postgres";
    public static String password = "geheim";

    //Data import
    public static String createTables = "create ...";
    public static String csvLocation = "C:\\Berufsschule\\Blockwoche\\DbBenchmark\\persons.csv";
    public static String importDataQuery = "COPY persons(first_name, last_name, dob, email)\n" +
            "            FROM '" + csvLocation + "'\n" +
            "            DELIMITER ','\n" +
            "            CSV HEADER;";

    //
    public static String CreateStatement = "";
    public static String ReadStatement = "";
    public static String UpdateStatement = "";
    public static String DeleteStatement = "";

    public void importData() throws SQLException, ClassNotFoundException{
        Statement statement = getStatement(getConnection());
        statement.execute(createTables);
        statement.execute(importDataQuery);
    }

    public Connection getConnection() throws SQLException, ClassNotFoundException {
        Class.forName("org.postgresql.Driver");
        return DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/postgres?user=postgres&password=geheim",
                "postgres",
                "geheim");
    }

    public Statement getStatement(Connection connection) throws SQLException{
        return connection.createStatement();
    }
}

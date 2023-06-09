import java.sql.*;
import org.neo4j.jdbc.*;

public class Neo4JConnection implements IDatabaseConnector {

    private Connection con;
    public boolean connect(){
        try{
            con = DriverManager.getConnection("jdbc:neo4j:bolt://localhost:7687", "java", "passwort");
            System.out.println("Verbindung zur Datenbank wurde hergestellt");
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public ResultSet ask(String query){
        try {
            Statement statement = con.createStatement();
            return statement.executeQuery(query);
        }
        catch (Exception e) {
            System.err.println("The Query failed: " + query);
            e.printStackTrace();
            return null;
        }
    }

    public boolean send(String query) {
        try {
            Statement statement = con.createStatement();
            statement.execute(query);
        }
        catch (Exception e) {
            System.err.println("The Query failed: " + query);
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean initializeDatabase(String... params) {
        return false;
    }
}

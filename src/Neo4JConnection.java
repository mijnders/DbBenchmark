import java.sql.*;
import org.neo4j.jdbc.*;

public class Neo4JConnection implements IDatabaseConnector {

    private Connection con;
    public boolean connect(){
        try(Connection con = DriverManager.getConnection("jdbc:neo4j:bolt://localhost:7687", "java", "passwort")){
            System.out.println("Verbindung zur Datenbank wurde hergestellt");
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public ResultSet ask(String query){
        try(PreparedStatement statement = con.prepareStatement(query)){
            try(ResultSet rs = statement.executeQuery()){
                return rs;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public boolean send(String query) {
        try(PreparedStatement statement = con.prepareStatement(query)){
            try(ResultSet rs = statement.executeQuery()){
                if(!rs.next()) return false;
                System.out.println(rs.getString(0));
                return true;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public boolean initializeDatabase(String... params) {
        return false;
    }
}

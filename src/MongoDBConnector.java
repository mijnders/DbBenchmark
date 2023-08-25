import java.sql.*;

public class MongoDBConnector implements IDatabaseConnector{

    private Connection con;
    public boolean connect(){
        try{
            con = DriverManager.getConnection("jdbc:mongodb://localhost:27017");
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

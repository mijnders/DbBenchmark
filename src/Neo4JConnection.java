import java.sql.*;
import org.neo4j.jdbc.*;
public class Neo4JConnection {

    public static void GetClass(){
        String url = "jdbc:neo4j:bolt://localhost:7687";
        String sql = "MATCH (n:Movie)-[:ACTED_IN]-(a:Person) WHERE a.name = \"Val Kilmer\" RETURN n.title";
        try (Connection con = DriverManager.getConnection(url, "java", "passwort")) {
            {
                try(PreparedStatement stmt = con.prepareStatement(sql)){
                    try(ResultSet rs = stmt.executeQuery()){
                        while(rs.next()){
                            System.out.println(rs.getString("n.title"));
                        }
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

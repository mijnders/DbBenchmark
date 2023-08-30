import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        var neo4jConnector = new Neo4JConnector();
        neo4jConnector.connect();
        neo4jConnector.initializeDatabase();
        try (ResultSet rs = neo4jConnector.ask("MATCH (n:Customer) RETURN n.customerId, n.name LIMIT 1000000")){
            while (rs.next()) {
                System.out.println(rs.getInt("n.customerId") + rs.getString("n.name"));
            }
        }
        catch(Exception e){

        }
    }
}
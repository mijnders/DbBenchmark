import java.sql.*;
import java.util.ArrayList;

public class Neo4JConnector implements IDatabaseConnector {

    //Connection string
    private static final String neo4jConnectionString = "jdbc:neo4j:bolt://localhost:7687";
    private static final String user = "neo4j";
    private static final String password = "passwort123";
    private Connection con;
    public boolean connect(){
        try {
            Class.forName("org.neo4j.driver.Driver");
            con = DriverManager.getConnection(
                    neo4jConnectionString,
                    user,
                    password);
            System.out.println("Connection established");
        }
        catch (Exception e) {
            System.err.println("Connection failed!");
            e.printStackTrace();
            return false;
        }
        return true;
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
         // Für 10 Customer-CSVs und 1 Car-CSV
        ArrayList<String> queries = new ArrayList<String>();
        //CREATE CONSTRAINT FOR CUSTOMER
        queries.add("CREATE CONSTRAINT customerIdConstraint FOR (customer:Customer) REQUIRE customer.id IS UNIQUE");
        queries.add("CREATE CONSTRAINT carIdConstraint FOR (car:Car) REQUIRE car.id IS UNIQUE");
        // READ CUSTOMER.CSV
        for (int i = 1; i <= 10; i++) {
            String customerQuery = "LOAD CSV WITH HEADERS FROM 'file:///Customer/Customer" + i + ".csv' AS line\n" +
                    "CREATE (:Customer {id: toInteger(line.Id), name: line.Name, street: line.Street, city: line.City});";
            queries.add(customerQuery);
        }
        // READ Cars
        String carQuery = "LOAD CSV WITH HEADERS FROM 'file:///Cars/Cars0.csv' AS line\n" +
                "CREATE (:Car {id: toInteger(line.Id), name: line.Name, baujahr: toInteger(line.Baujahr)});";
        queries.add(carQuery);
        for (String query : queries) {
            send(query);
        }
        return true;
    }
}

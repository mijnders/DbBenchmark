import java.sql.*;
public class Neo4JConnector implements IDatabaseConnector {

    //Connection string
    private static final String neo4jConnectionString = "jdbc:neo4j:bolt://localhost:7687";
    private static final String user = "neo4j";
    private static final String password = "passwort";
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
        String[] queries = new String[11]; // Für 10 Customer-CSVs und 1 Car-CSV

        // READ CUSTOMER.CSV
        for (int i = 1; i <= 10; i++) {
            String customerQuery = "LOAD CSV WITH HEADERS FROM 'file:///Customer/Customer" + i + ".csv' AS line\n" +
                    "CREATE (:Customer {firstname: line.Firstname, lastname: line.Lastname, street: line.Street, city: line.City})";
            queries[i - 1] = customerQuery;
        }

        // READ Cars
        String carQuery = "LOAD CSV WITH HEADERS FROM 'file:///Cars/Cars0.csv' AS line\n" +
                "CREATE (:Car {modell: line.Modell, baujahr: toInteger(line.Baujahr)})";
        queries[10] = carQuery;

        for (String query : queries) {
            send(query);
        }

        return true;
    }
}

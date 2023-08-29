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
        String customerQuery = "LOAD CSV WITH HEADERS FROM 'file:///Customer/Customer0.csv' AS line\n" +
                    "CREATE (:Customer {customerId: toInteger(line.Id), name: line.Name, street: line.Street, city: line.City});";
        var answer = send(customerQuery);
        if(!answer){
            return false;
        }
        // READ Cars
        String carQuery = "LOAD CSV WITH HEADERS FROM 'file:///Cars/Cars0.csv' AS line\n" +
                "CREATE (:Car {carId: toInteger(line.Id), name: line.Name, baujahr: toInteger(line.Baujahr)});";

        answer = send(carQuery);
        if(!answer){
            return false;
        }
        return true;
    }
}

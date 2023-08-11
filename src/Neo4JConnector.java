import java.sql.*;

public class Neo4JConnector implements IDatabaseConnector {

    //Connection string
    private static final String neo4jConnectionString = "jdbc:neo4j:bolt://localhost:7687";
    private static final String user = "java";
    private static final String password = "passwort";
    private Connection con;
    public boolean connect(){
        try {
            Class.forName("org.neo4j.driver.Driver");
            con = DriverManager.getConnection(
                    neo4jConnectionString,
                    user,
                    password);
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
        var query = "";
        //READ CUSTOMER.CSV
        for (int i = 1; i <= 10; i++){
            query += "LOAD CSV WITH HEADERS FROM 'file:///Customer/Customer" + i + ".csv' " +
                    "LOAD CSV WITH HEADERS FROM 'file:///Products/Product" + i + ".csv' " +
                    "LOAD CSV WITH HEADERS FROM 'file:///Invoices/Invoice" + i + ".csv' ";
        }
        for (int i = 1; i <= 50; i++){
            query += "LOAD CSV WITH HEADERS FROM 'file:///Items/Item" + i + ".csv' ";
        }
        send(query);
        return true;
    }
}

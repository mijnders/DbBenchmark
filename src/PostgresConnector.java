import java.sql.*;

public class PostgresConnector implements IDatabaseConnector{

    //Connection string
    public static String postgresURL = "jdbc:postgresql://localhost:5432/postgres?user=postgres&password=geheim";
    public static String user = "postgres";
    public static String password = "geheim";

    //Data import
    public static String createTables =
                    "create table Product (id INT PRIMARY KEY, Name TEXT, Price FLOAT);" +
                    "create table Customer (id INT PRIMARY KEY, Firstname TEXT, Lastname TEXT, Street TEXT, City TEXT);" +
                    "create table Invoice (id INT PRIMARY KEY, CustomerID INT, Total FLOAT, CONSTRAINT customer_fk FOREIGN KEY(CustomerID) REFERENCES Customer(id));"+
                    "create table Item (InvoiceID INT, Item INT, ProductID INT, Quantity INT, Cost FLOAT, PRIMARY KEY(InvoiceID, Item), " +
                            "CONSTRAINT invoice_fk FOREIGN KEY(InvoiceID) REFERENCES Invoice(id), CONSTRAINT product_fk FOREIGN KEY(ProductID) REFERENCES Product(id));";

    private Connection connection;

    @Override
    public boolean connect() {
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5432/postgres?user=postgres&password=geheim",
                    "postgres",
                    "geheim");
        }
        catch (Exception e) {
            System.err.println("Connection failed!");
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public ResultSet ask(String query) {
        try {
            Statement statement = connection.createStatement();
            return statement.executeQuery(query);
        }
        catch (Exception e) {
            System.err.println("The Query failed: " + query);
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean send(String query) {
        try {
            Statement statement = connection.createStatement();
            statement.execute(query);
        }
        catch (Exception e) {
            System.err.println("The Query failed: " + query);
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean initializeDatabase(String... params) {
        String importQuery =
                "COPY Customer(id, Firstname, Lastname, Street, City) FROM '" + params[0] + "'DELIMITER ',' CSV HEADER;" +
                "COPY Product(id, Name, Price) FROM '" + params[1] + "' DELIMITER ',' CSV HEADER;" +
                "COPY Invoice(id, CustomerID, Total) '" + params[2] + "' DELIMITER ',' CSV HEADER;" +
                "COPY Item(InvoiceID, Item, ProductID, Quantity, Cost) '" + params[3] + "' DELIMITER ',' CSV HEADER;";

        return send(importQuery);
    }
}
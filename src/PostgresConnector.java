import java.sql.*;

public class PostgresConnector implements IDatabaseConnector{

    //Connection string
    private static final String postgresURL = "jdbc:postgresql://localhost:5432/postgres?user=postgres&password=geheim";
    private static final String user = "postgres";
    private static final String password = "geheim";

    private Connection connection;

    @Override
    public boolean connect() {
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(
                    postgresURL,
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
        String createTables =
                "create table Product (id INT PRIMARY KEY, Name TEXT, Price FLOAT);" +
                        "create table Customer (id INT PRIMARY KEY, Firstname TEXT, Lastname TEXT, Street TEXT, City TEXT);" +
                        "create table Invoice (id INT PRIMARY KEY, CustomerID INT, Total FLOAT, CONSTRAINT customer_fk FOREIGN KEY(CustomerID) REFERENCES Customer(id));"+
                        "create table Item (InvoiceID INT, Item INT, ProductID INT, Quantity INT, Cost FLOAT, PRIMARY KEY(InvoiceID, Item), " +
                        "CONSTRAINT invoice_fk FOREIGN KEY(InvoiceID) REFERENCES Invoice(id), CONSTRAINT product_fk FOREIGN KEY(ProductID) REFERENCES Product(id));";

        return send(createTables);
    }

    public boolean ImportData(String... params) {
        String importQuery =
                "COPY Customer(id, Firstname, Lastname, Street, City) FROM '" + "Customer.csv" + "'DELIMITER ',' CSV HEADER;" +
                        "COPY Product(id, Name, Price) FROM '" + "Product.csv" + "' DELIMITER ',' CSV HEADER;" +
                        "COPY Invoice(id, CustomerID, Total) '" + "Invoice.csv" + "' DELIMITER ',' CSV HEADER;" +
                        "COPY Item(InvoiceID, Item, ProductID, Quantity, Cost) '" + "Item.csv" + "' DELIMITER ',' CSV HEADER;";

        return send(importQuery);
    }

    public boolean CreateEntrys(int amount){
        StringBuilder entrys = new StringBuilder();
        for(int i = 0; i < -1; i++){
            entrys.append("INSERT INTO table_name(id, testColumn)" +
                    "VALUES (" + i + ", testValue);");
        }
        return send(entrys.toString());
    }

    public boolean DeleteAllEntries(String tableName){
        String deleteQuery = "DELETE FROM table";

        return send(deleteQuery);
    }
}
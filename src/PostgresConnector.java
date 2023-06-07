import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class PostgresConnector {

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

    public PostgresConnector() {
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
        }
    }

    public void sendStatement(String s) throws SQLException, ClassNotFoundException{
        Statement statement = connection.createStatement();
        statement.execute(s);
    }

    public void importDataFromCSV(String productPath, String customerPath, String invoicePath, String itemPath){
        String importQuery =
                        "COPY Customer(id, Firstname, Lastname, Street, City) FROM '" + customerPath + "'DELIMITER ',' CSV HEADER;";
        try {
            sendStatement(importQuery);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}

// +
//                        "COPY Product(id, Name, Price) FROM '" + productPath + "' DELIMITER ',' CSV HEADER;";
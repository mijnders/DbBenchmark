import java.sql.*;

public class PostgresConnector implements IDatabaseConnector{

    //Connection string
    private static final String postgresURL = "jdbc:postgresql://localhost:5432/postgres?user=postgres&password=geheim";
    private static final String user = "postgres";
    private static final String password = "geheim";
    private static final String testFiles = System.getProperty("user.dir") + "\\CSV\\";

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
                "create table Cars (id INT PRIMARY KEY, Name TEXT, Baujahr INT);" +
                "create table Customers (id INT PRIMARY KEY, Name TEXT, Street TEXT, City TEXT);" +
                "create table Owners (CustomerID INT, CarID INT, CarColor TEXT, Date TEXT" + ");";
                //", CONSTRAINT customer_fk FOREIGN KEY(CustomerID) REFERENCES Customers(id), CONSTRAINT car_fk FOREIGN KEY(CarID) REFERENCES Cars(id));";


        return send(createTables);
    }

    public boolean ImportData(String... params) {
        String importQuery =
                "COPY Customers(id, Name, Street, City) FROM '" + testFiles + "Customer\\Customer1.csv" + "' DELIMITER ',' CSV HEADER;" +
                "COPY Cars(id, Name, Baujahr) FROM '" + testFiles + "Cars\\Cars0.csv" + "' DELIMITER ',' CSV HEADER;" +
                "COPY Owners(CustomerID, CarID, CarColor, Date) FROM '" + testFiles + "Owners\\Owners1.csv" + "' DELIMITER ',' CSV HEADER;";

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

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
        var connector = new Neo4JConnector();
        connector.connect();
        connector.initializeDatabase();
        }
    }